package net.mrlatte.khanjar.devices;

/**
 * Created by Jongha on 2/7/16.
 */
public class Version implements Comparable<Version> {

    private String version;

    public Version(String version) {
        if (version == null)
            throw new IllegalArgumentException("Version can not be null");

        if (!version.matches("[0-9]+(\\.[0-9]+)*"))
            throw new IllegalArgumentException("Invalid version format");

        this.version = version;
    }

    public final String get() {
        return this.version;
    }

    @Override
    public int compareTo(Version nowVersion) {
        return compareToWithPosition(nowVersion).getResult();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (this.getClass() != that.getClass())
            return false;
        return this.compareTo((Version) that) == 0;
    }

    public CompareToPosition compareToWithPosition(Version nowVersion) {
        if (nowVersion == null) {
            return new CompareToPosition(0, 1);
        }

        String[] thisParts = this.get().split("\\.");
        String[] thatParts = nowVersion.get().split("\\.");

        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ?
                    Integer.parseInt(thisParts[i]) : 0;

            int thatPart = i < thatParts.length ?
                    Integer.parseInt(thatParts[i]) : 0;

            if (thisPart < thatPart) {
                return new CompareToPosition(i, -1);
            }

            if (thisPart > thatPart) {
                return new CompareToPosition(i, 1);
            }
        }

        return new CompareToPosition(0, 0);
    }

    /**
     * x.x.x.x
     * 1st, 2nd: force update
     * 3rd: update notification
     * 4th: normal (no notification)
     */
    public class CompareToPosition {
        private int position;
        private int result;

        public CompareToPosition(int position, int result) {
            this.position = position;
            this.result = result;
        }

        public boolean isForceUpdate() {
            return isOutOfDate() && position < 2;
        }

        public boolean isNotification() {
            return isOutOfDate() && position < 3;
        }

        public boolean isOutOfDate() {
            return result > 0;
        }

        public int getPosition() {
            return position;
        }

        public int getResult() {
            return result;
        }
    }
}
