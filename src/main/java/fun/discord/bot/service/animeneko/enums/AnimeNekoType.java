package fun.discord.bot.service.animeneko.enums;

public enum  AnimeNekoType {
    NEKO("neko", false),
    KITSUNE("kitsune", false),
    HUG("hug", false),
    PAT("pat", false),
    WAIFU("waifu", false),
    CRY("cry", false),
    KISS("kiss", false),
    SLAP("slap", false),
    SMUG("smug", false),
    PUNCH("punch", false),
    NEKOLEWD("nekolewd", true);

    private final String urlStr;
    private final boolean nsfw;

    AnimeNekoType(String urlStr, boolean nsfw) {
        this.urlStr = urlStr;
        this.nsfw = nsfw;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public boolean isNsfw() {
        return nsfw;
    }
}
