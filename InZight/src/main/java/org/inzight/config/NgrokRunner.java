package org.inzight.config;

import java.io.File;

public class NgrokRunner {

    //Cấu hình domain & đường dẫn ngrok

    private static final String NGROK_PATH = System.getenv("NGROK_PATH");
    private static final String NGROK_DOMAIN = System.getenv("NGROK_DOMAIN");
    private static final int LOCAL_PORT = 8080;

    public static void startNgrok() {
        if (!"true".equalsIgnoreCase(System.getenv("ENABLE_NGROK"))
                || NGROK_PATH == null || NGROK_DOMAIN == null) {
            return;
        }
        try {
            File ngrokFile = new File(NGROK_PATH);
            if (!ngrokFile.exists()) {
                System.err.println("Không tìm thấy file ngrok tại: " + NGROK_PATH);
                return;
            }

            String[] command = {
                    "cmd.exe", "/c",
                    "start \"Ngrok Tunnel\" \"" + NGROK_PATH + "\" http --domain=" + NGROK_DOMAIN + " " + LOCAL_PORT
            };

            new ProcessBuilder(command).start();
            System.out.println("Ngrok tunnel started on https://" + NGROK_DOMAIN);

        } catch (Exception e) {
            System.err.println("Lỗi khi chạy ngrok: " + e.getMessage());
        }
    }

}
