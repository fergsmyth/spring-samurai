package com.genericgames.samurai.ai.routefinding;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private List<MapNode> mapNodes;
    /**
     * Indicates whether this route is stale
     * and therefore needs to be refreshed with a new one.
     */
    private boolean stale = false;
    /**
     * The number of frames for which the route has been used.
     */
    int lifeTimeInFrames = 0;


    public Route(){
        mapNodes = new ArrayList<MapNode>();
    }

    public void incrementLifeTime(){
        lifeTimeInFrames++;
        if(lifeTimeInFrames>60){
            stale = true;
        }
    }

    public List<MapNode> getMapNodes() {
        return mapNodes;
    }

    public void setMapNodes(List<MapNode> mapNodes) {
        this.mapNodes = mapNodes;
    }

    public boolean isStale() {
        return stale;
    }
}
