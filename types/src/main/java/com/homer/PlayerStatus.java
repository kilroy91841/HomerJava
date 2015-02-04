package com.homer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by golub on 2/4/15.
 */
public class PlayerStatus {

    public static final PlayerStatus ACTIVE = new PlayerStatus("ACTIVE", "A");
    public static final PlayerStatus DISABLEDLIST = new PlayerStatus("DISABLED LIST", "DL");
    public static final PlayerStatus MINORS = new PlayerStatus("MINORS", "MIN");
    public static final PlayerStatus FREEAGENT = new PlayerStatus("FREEAGENT", "FA");
    public static final PlayerStatus RESTRICTED = new PlayerStatus("RESTRICTED", "RST");

    static {
        PlayerStatus.map.put(ACTIVE.getName(), ACTIVE);
        PlayerStatus.map.put(DISABLEDLIST.getName(), DISABLEDLIST);
        PlayerStatus.map.put(MINORS.getName(), MINORS);
        PlayerStatus.map.put(FREEAGENT.getName(), FREEAGENT);
        PlayerStatus.map.put(RESTRICTED.getName(), RESTRICTED);
    }

    private String name;
    private String code;
    protected static final Map<String, PlayerStatus> map = new HashMap<String, PlayerStatus>();
    private PlayerStatus(String name, String code) {
        this.name = name;
        this.code = code;
    }
    public String getName() { return name; }
    public String getCode() { return code; }
    public static PlayerStatus get(String name) throws Exception {
        PlayerStatus status = map.get(name);
        if(map == null) {
            throw new Exception("PlayerStatus not found for name: " + name);
        }
        return status;
    }
    @Override
    public String toString() {
        return "PlayerStatus{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
