package com.github.ZXSkelobrine.stock.global.updates;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.management.windows.notifications.NotificationWindow;
import com.github.ZXSkelobrine.stock.management.windows.notifications.NotificationWindow.ButtonTypes;
import com.github.ZXSkelobrine.stock.management.windows.notifications.UpdateWindow;

public class UpdateManager {

	private static String downloadedFileName;
	public static boolean updateAvailable = false;
	private static String versionAppend;

	private static void launchUpdateProcedure() throws IOException {
		final File output = new File(System.getProperty("user.dir") + System.getProperty("file.separator"));
		if (!output.exists()) output.mkdirs();
		File toCopy = new File(Launcher.BASE_SAVE_PATH + "/updates/downloaded/" + downloadedFileName);
		try {
			Runtime.getRuntime().exec("cmd /c MOVE /Y \"" + toCopy.getAbsolutePath() + "\" \"" + output.getAbsolutePath() + "\"");
			NotificationWindow.launchWindow(ButtonTypes.OKAY_ONLY, "New version downloaded and saved to: " + output.getAbsolutePath() + " this program will now close and launch the new version. Feel free to delete this file and start using the new version.", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						launchNewVersion(versionAppend);
					} catch (IOException e) {
						// Print a brief description of the error.
						System.out.println("[Launch Update Procedure]: Error launching new version(IOException). Contact the author or run this program with -showSTs to print the stack traces.");
						// If it does not print the stack trace for error
						// logging if it
						// is enabled.
						if (Launcher.PRINT_STACK_TRACES) {
							e.printStackTrace();
						}
					}
					System.exit(0);
				}
			}, null, null);
		} catch (Exception e) {
			// Print a brief description of the error.
			System.out.println("[Launch Update Procedure]: Unknown error(Exception). Contact the author or run this program with -showSTs to print the stack traces.");
			// If it does not print the stack trace for error logging if it
			// is enabled.
			if (Launcher.PRINT_STACK_TRACES) {
				e.printStackTrace();
			}
		}
	}

	private static void downloadFile(String version) throws IOException {
		String link = "https://raw.githubusercontent.com/ZXSkelobrine/New-Hook-Stock-Solutions/master/Files/New%20Hook%20Stock%20Solutions-" + version + ".jar";
		URL url = new URL(link);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		Map<String, List<String>> header = http.getHeaderFields();
		while (isRedirected(header)) {
			link = header.get("Location").get(0);
			url = new URL(link);
			http = (HttpURLConnection) url.openConnection();
			header = http.getHeaderFields();
		}
		InputStream input = http.getInputStream();
		byte[] buffer = new byte[4096];
		int n = -1;
		File outputDirectory = new File(Launcher.BASE_SAVE_PATH + "/updates/downloaded/");
		if (!outputDirectory.exists()) outputDirectory.mkdirs();
		OutputStream output = new FileOutputStream(new File(Launcher.BASE_SAVE_PATH + "/updates/downloaded/" + downloadedFileName));
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
		}
		output.close();
		launchUpdateProcedure();
	}

	private static boolean isRedirected(Map<String, List<String>> header) {
		for (String hv : header.get(null)) {
			if (hv.contains(" 301 ") || hv.contains(" 302 ")) return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	public static void launchNewVersion(String version) throws IOException {
		String sep = System.getProperty("file.separator");
		String javaPath = System.getProperty("java.home") + sep + "bin" + sep + "java.exe";
		String workingDirectory = System.getProperty("user.dir") + sep;
		ProcessBuilder pb = new ProcessBuilder(javaPath, "-jar", "New Hook Stock Solutions-" + version + ".jar");
		pb.directory(new File(workingDirectory));
		Process p = pb.start();
	}

	public static boolean checkForUpdate() throws Throwable {
		Document doc = Jsoup.parse(new URL("https://github.com/ZXSkelobrine/New-Hook-Stock-Solutions/tree/master/Files"), 3000);
		Elements files = doc.getElementsByClass("js-directory-link");
		for (Element e : files) {
			if (e.text().startsWith("New Hook Stock Solutions")) {
				String formatting = e.text();
				formatting = formatting.substring(0, formatting.length() - 4);
				formatting = formatting.substring(25);
				int major = Integer.parseInt(Character.toString(formatting.charAt(0)));
				int export = Integer.parseInt(Character.toString(formatting.charAt(2)));
				int minor = Integer.parseInt(Character.toString(formatting.charAt(4)));
				downloadedFileName = "New Hook Stock Solutions-" + major + "." + export + "." + minor + ".jar";
				versionAppend = major + "." + export + "." + minor;
				if (major > Launcher.MAJOR_VERSION || export > Launcher.EXPORT_VERSION || minor > Launcher.MINOR_VERSION) {
					updateAvailable = true;
				}
			}
		}
		return updateAvailable;
	}

	public static void processUpdate() {
		UpdateWindow.launchWindow("New version available: " + versionAppend);
	}

	public static void startUpdate() throws IOException {
		downloadFile(versionAppend);
	}

}
