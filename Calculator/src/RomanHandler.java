import java.util.HashMap;
import java.util.Map;
/** Класс-обработчик римских цифр */
class RomanHandler {
    char [] firstVal;
    char [] secondVal;
    Map<Character, Integer> romanValue = new HashMap<>();
    Map<Integer, Character> arabianValue = new HashMap<>();
    public RomanHandler(){
        setMapValue();
    }

    /**В идеале убрать инициализацию мапы вывести за пределы класса*/
    private void setMapValue() {
        romanValue.put('I', 1);
        romanValue.put('V', 5);
        romanValue.put('X', 10);
        romanValue.put('L', 50);
        romanValue.put('C', 100);
        arabianValue.put(1, 'I');
        arabianValue.put(5, 'V');
        arabianValue.put(10, 'X');
        arabianValue.put(50, 'L');
        arabianValue.put(100, 'C');
    }
    /** Проверка введенной строки на наличие в ней римских цифр */
    public boolean testNum(String[] exp){
        firstVal = exp[0].toCharArray();
        secondVal = exp[2].toCharArray();
        try {
            if (testVal(firstVal) & !testVal(secondVal)){
                throw new NumberFormatException();
            }
            if (!testVal(firstVal) & testVal(secondVal)){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e){
            System.err.println("Используются одновременно разные системы счисления");
            System.exit(0);
        }
        return testVal(secondVal) & testVal(firstVal);
    }
    private boolean testVal(char[] val) {
        int count = 0;
        for (char c : val) {
            for (Map.Entry<Character, Integer> entry : romanValue.entrySet()) {
                if (c == entry.getKey()) {
                    count++;
                }
            }
        }
        int len = val.length;
        return count == len;
    }
    /** Метод, преобразующий римское число в арабское */
    public String romToArab(String s) {
        char [] val = s.toCharArray();
        Integer [] intVal = new Integer[val.length];
        for (int i = 0; i<val.length;i++){
            for (Map.Entry<Character,Integer> entry: romanValue.entrySet()){
                if (val[i] == entry.getKey() ){
                    intVal[i] = entry.getValue();
                }
            }
        }
        for (int i = intVal.length-1; i>=0;i--){
            if (i-1 >= 0) {
                if (intVal[i] > intVal[i - 1]) {
                    intVal[i-1] = intVal[i] - intVal[i-1];
                } else {
                    intVal[i-1] = intVal[i] + intVal[i-1];
                    if(i-2 >=0){
                        if (intVal[i].equals(intVal[i - 2])){
                            intVal[i-2] = intVal[i-1] + intVal[i-2];
                            i--;
                        }
                    }
                }
            }
        }
        return String.valueOf(intVal[0]);
    }
    /** Метод, преобразуюзий арабскую цифру в римскую */
    public String arabToRom(Integer value) throws ArithmeticException {
        Integer remainder = value;
        var count = 0;
        StringBuilder romValue = new StringBuilder();
        if (value < 1){
            throw new ArithmeticException();
        }else {
            Integer [] ar = {100, 50, 10, 5, 1};
            for (int i = 0; i<ar.length; i++){
                if (remainder / ar[i] > 0){
                    count = remainder / ar[i];
                    for(int j = 0; j < count; j++) {
                        romValue.append(arabianValue.get(ar[i]));
                        remainder = remainder - ar[i];
                    }
                }else{
                    if (i+1<ar.length ){
                        if( ar[i]/ar[i+1] == 2){
                            if (i+2<ar.length){
                                if(ar[i]/ar[i+2] != 2){
                                    if (ar[i] - ar[i+2] <= remainder){
                                        romValue.append(arabianValue.get(ar[i + 2])).append(arabianValue.get(ar[i]));
                                        remainder = remainder - ar[i] + ar[i+2];
                                    }
                                }
                            }
                        } else{
                            if (ar[i] - ar[i+1] <= remainder){
                                romValue.append(arabianValue.get(ar[i + 1])).append(arabianValue.get(ar[i]));
                                remainder = remainder - ar[i] + ar[i+1];
                            }
                        }
                    }
                }
            }
        }
        return romValue.toString();
    }
}
