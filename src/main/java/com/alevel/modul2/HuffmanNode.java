package com.alevel.modul2;

public class HuffmanNode implements Comparable<HuffmanNode> {
    private Character symbol;
    private Integer waight;
    HuffmanNode lLeaf, rLeaf;

    public HuffmanNode(Character symbol, Integer waight) {
        this.symbol = symbol;
        this.waight = waight;
    }

    public HuffmanNode(HuffmanNode lLeaf, HuffmanNode rLeaf) {
        this.waight = lLeaf.getWaight() + rLeaf.getWaight();
        this.lLeaf = lLeaf;
        this.rLeaf = rLeaf;
    }

    public Character getSymbol() {
        return symbol;
    }

    public Integer getWaight() {
        return waight;
    }

    public HuffmanNode getlLeaf() {
        return lLeaf;
    }

    public HuffmanNode getrLeaf() {
        return rLeaf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HuffmanNode that = (HuffmanNode) o;

        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;
        if (waight != null ? !waight.equals(that.waight) : that.waight != null) return false;
        if (rLeaf != null ? !rLeaf.equals(that.rLeaf) : that.rLeaf != null) return false;
        return lLeaf != null ? lLeaf.equals(that.lLeaf) : that.lLeaf == null;
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (waight != null ? waight.hashCode() : 0);
        result = 31 * result + (rLeaf != null ? rLeaf.hashCode() : 0);
        result = 31 * result + (lLeaf != null ? lLeaf.hashCode() : 0);
        return result;
    }

    public int compareTo(HuffmanNode o) {
        return this.waight - o.waight;
    }

    @Override
    public String toString() {
        if (this.getSymbol()==null) {
            return "(Node:" + waight + ") {L:" + lLeaf + " R:" + rLeaf + "}";
        } else
            return "(Leaf:(" + symbol + ":" + waight + ")";
    }

}
