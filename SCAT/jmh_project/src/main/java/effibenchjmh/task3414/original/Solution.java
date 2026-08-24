package effibenchjmh.task3414.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    private enum Direction {
        UP,
        DOWN;
    };

    private record Move(Direction lastDirection, int currentStair, int currentJump) {

        public Move down() {
            return new Move(Direction.DOWN, currentStair - 1, currentJump);
        }

        public Move up() {
            return new Move(Direction.UP, currentStair + (1 << currentJump), currentJump + 1);
        }
    }


    private int helper(Move move, int k, Map<Move,Integer> cache) { 
        if(move.currentStair - k > 1)
            return 0;

        if(cache.containsKey(move))
            return cache.get(move);

        var res = move.currentStair == k? 1: 0;    

        if(move.lastDirection == Direction.UP && move.currentStair > 0) {
            res += helper(move.down(), k, cache);
        }

        res += helper(move.up(), k, cache);

        cache.put(move, res);

        return res;
    }

    public int waysToReachStair(int k) {
        return helper(new Move(Direction.UP, 1, 0), k, new HashMap<>());
    }
}
