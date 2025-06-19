public class SessionManager {
    private static String activeKey = null;

    public static void setActiveKey(String key) {
        activeKey = key;
    }

    /**
     * Retrieves the active session key.
     * @return the active session key.
     * @throws IllegalStateException if no session is active.
     */
    public static String getActiveKey() {
        if (activeKey == null) {
            throw new IllegalStateException("No active session. User must log in first.");
        }
        return activeKey;
    }

    public static void clearActiveKey() {
        activeKey = null;
    }

    public static boolean isSessionActive() {
        return activeKey != null;
    }
}
