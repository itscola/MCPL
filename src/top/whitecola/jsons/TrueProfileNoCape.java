package top.whitecola.jsons;

public class TrueProfileNoCape {



    private long timestamp;
    private String profileId;
    private String profileName;
    private TexturesBean textures;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public TexturesBean getTextures() {
        return textures;
    }

    public void setTextures(TexturesBean textures) {
        this.textures = textures;
    }

    public static class TexturesBean {


        private SKINBean SKIN;

        public SKINBean getSKIN() {
            return SKIN;
        }

        public void setSKIN(SKINBean SKIN) {
            this.SKIN = SKIN;
        }

        public static class SKINBean {


            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
