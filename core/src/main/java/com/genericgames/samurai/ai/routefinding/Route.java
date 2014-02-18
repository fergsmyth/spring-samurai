package com.genericgames.samurai.ai.routefinding;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private static final int ROUTE_LIFE_TIME = 30;

    private List<MapNode> mapNodes;

    private MapNode currentTargetNode;
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
        setMapNodes(new ArrayList<MapNode>());
    }

    public Route(List<MapNode> mapNodes){
        setMapNodes(mapNodes);
    }

    public void incrementLifeTime(){
        lifeTimeInFrames++;
        if(lifeTimeInFrames > ROUTE_LIFE_TIME){
            setStale(true);
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

    public void setStale(boolean stale) {
        this.stale = stale;
    }

    public MapNode getCurrentTargetNode() {
        return currentTargetNode;
    }

    public void setCurrentTargetNode(MapNode currentTargetNode) {
        this.currentTargetNode = currentTargetNode;
    }
}
