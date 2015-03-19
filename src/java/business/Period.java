/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author Peter
 */
public class Period {

    private int id;
    private int startweeknumber;
    private int endWeeknumber;

    public Period() {
        this.startweeknumber = 1;
        this.endWeeknumber = 53;
    }

    public Period(int startweeknumber, int endWeeknumber) {
        this.startweeknumber = startweeknumber;
        this.endWeeknumber = endWeeknumber;
    }

    public int getStartweeknumber() {
        return startweeknumber;
    }

    public int getEndWeeknumber() {
        return endWeeknumber;
    }

    public void setStartweeknumber(int startweeknumber) {
        this.startweeknumber = startweeknumber;
    }

    public void setEndWeeknumber(int endWeeknumber) {
        this.endWeeknumber = endWeeknumber;
    }
}
