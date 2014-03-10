package com.genericgames.samurai.ai.routefinding;

import java.util.ArrayList;
import java.util.Collection;

public class RouteCostMap {

    private int width;
    private int height;
    private Collection<MapNode> nodes;

    public RouteCostMap(){
    }

    public RouteCostMap(int width, int height){
        nodes = new ArrayList<MapNode>();
        this.width = width;
        this.height = height;
        initialiseNodes();
    }

    /**
     * Sets each node's cost to a default value of 1.
     */
    private void initialiseNodes() {
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                MapNode mapNode = new MapNode(i, j);
                mapNode.setCost(0);
                nodes.add(mapNode);
            }
        }
    }

    public Collection<MapNode> getNodes(){
        return nodes;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setNodes(Collection<MapNode> nodes) {
        this.nodes = nodes;
    }

    public RouteCostMap copy(){
        RouteCostMap copy = new RouteCostMap();
        copy.setWidth(this.getWidth());
        copy.setHeight(this.getHeight());
        copy.setNodes(new ArrayList<MapNode>(this.getNodes()));
        return copy;
    }
}
