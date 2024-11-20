import java.util.*;

public class GreedyAI extends AIPlayer{


    public GreedyAI(boolean isPlayerOne) {

        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        GameLogic gameLogic = (GameLogic) gameStatus;
        Player player=gameLogic.getCurrentPlayer();
        List<Position> valid=gameLogic.ValidMoves();
        Disc disc;
        List<Integer> countValid=new ArrayList<>();
        int max=0;
        int indexMax=0;
        List<Position> sameCount=new ArrayList<>();
        for(int i=0;i<valid.size();i++){
            Position p=valid.get(i);
            int currentCount=gameLogic.countFlips(p);
            countValid.add(currentCount);
            if(currentCount>max){
                max=currentCount;
                indexMax=i;
                sameCount.clear();
                sameCount.add(valid.get(i));
            } else if (currentCount==max) {
                sameCount.add(valid.get(i));
            }
        }
        disc= new SimpleDisc(player);
        if(sameCount.size()==1){
            return new Move(disc,valid.get(indexMax),null);
        }
        else{
            Collections.sort(sameCount,new MaxRelevantComparator());
            for (int i = 0; i < sameCount.size(); i++) {
                System.out.println(sameCount.get(i));
            }
            return new Move(disc,sameCount.get(sameCount.size()-1),null);
        }
    }
    class MaxRelevantComparator implements Comparator<Position> {
        @Override
        public int compare(Position p1, Position p2) {
            if(Integer.compare(p1.col(), p2.col())!=0) return Integer.compare(p1.col(), p2.col());
            else return Integer.compare(p1.row(), p2.row());
        }
    }
}

