package com.homer.baseball;

/**
 * Created by arigolub on 1/31/15.
 */
public class ThirdPartyPlayerInfo {

    public static ThirdPartyProvider MLB    = new ThirdPartyProvider("MLB");
    public static ThirdPartyProvider ESPN   = new ThirdPartyProvider("ESPN");

    private Player player;
    private long thirdPartyPlayerId;
    private ThirdPartyProvider provider;

    public ThirdPartyPlayerInfo() { }

    public ThirdPartyPlayerInfo(Player player, long thirdPartyPlayerId, ThirdPartyProvider provider) {
        this.player = player;
        this.thirdPartyPlayerId = thirdPartyPlayerId;
        this.provider = provider;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getThirdPartyPlayerId() {
        return thirdPartyPlayerId;
    }

    public void setThirdPartyPlayerId(long thirdPartyPlayerId) {
        this.thirdPartyPlayerId = thirdPartyPlayerId;
    }

    public ThirdPartyProvider getProvider() {
        return provider;
    }

    public void setProvider(ThirdPartyProvider provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "ThirdPartyPlayerInfo{" +
                "thirdPartyPlayerId=" + thirdPartyPlayerId +
                ", provider=" + provider +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThirdPartyPlayerInfo that = (ThirdPartyPlayerInfo) o;

        if (thirdPartyPlayerId != that.thirdPartyPlayerId) return false;
        if (player != null ? !player.equals(that.player) : that.player != null) return false;
        if (!provider.equals(that.provider)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (int) (thirdPartyPlayerId ^ (thirdPartyPlayerId >>> 32));
        result = 31 * result + provider.hashCode();
        return result;
    }

    public static class ThirdPartyProvider {

        private String name;

        private ThirdPartyProvider(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ThirdPartyProvider{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ThirdPartyProvider that = (ThirdPartyProvider) o;

            if (!name.equals(that.name)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
