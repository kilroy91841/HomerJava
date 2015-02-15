package com.homer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by golub on 2/4/15.
 */
@Entity
@Table(name="PLAYERSTATUS")
public class PlayerStatus {

    public static final PlayerStatus ACTIVE = new PlayerStatus("ACTIVE", "A");
    public static final PlayerStatus INACTIVE = new PlayerStatus("INACTIVE", "I");
    public static final PlayerStatus DISABLEDLIST = new PlayerStatus("DISABLED LIST", "DL");
    public static final PlayerStatus MINORS = new PlayerStatus("MINORS", "MIN");
    public static final PlayerStatus FREEAGENT = new PlayerStatus("FREEAGENT", "FA");
    public static final PlayerStatus RESTRICTED = new PlayerStatus("RESTRICTED", "RST");
    public static final PlayerStatus UNKNOWN = new PlayerStatus("UNKNOWN", "UNK");

    protected static final Map<String, PlayerStatus> map = new HashMap<String, PlayerStatus>();

    static {
        map.put(ACTIVE.getCode(), ACTIVE);
        map.put(INACTIVE.getCode(), INACTIVE);
        map.put(DISABLEDLIST.getCode(), DISABLEDLIST);
        map.put(MINORS.getCode(), MINORS);
        map.put(FREEAGENT.getCode(), FREEAGENT);
        map.put(RESTRICTED.getCode(), RESTRICTED);
        map.put(UNKNOWN.getCode(), UNKNOWN);
    }

    @Id
    @Column(name="playerStatusCode")
    private String code;
    @Column(name="playerStatusName")
    private String name;

    public PlayerStatus() { }

    private PlayerStatus(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public static PlayerStatus get(String code) throws Exception {
        PlayerStatus status = map.get(code);
        if(map == null) {
            throw new Exception("PlayerStatus not found for code: " + code);
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
