import java.util.ArrayList;

public class TwoSum {


    // 3.1
    public ArrayList<Integer> twoSum(ArrayList<Integer> values, int target) {

        for(int i = 0; i < values.size(); i++) {
            for(int j = i + 1; j < values.size(); j++) { // consider all pairs in the list

                // if they sum to target - success!
                if(values.get(i) + values.get(j) == target) {
                    ArrayList<Integer> answer = new ArrayList<>();
                    answer.add(i);
                    answer.add(j);

                    return answer;
                }
            }
        }

        return new ArrayList<>(); // no answer found

    }

    // 3.2
    // O(n^2)

    // 3.3
    // O(n^k)

}
