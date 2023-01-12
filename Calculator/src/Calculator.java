/**Класс для решения примеров вводимых строчкой*/
class Calculator {
    Integer firstNumber;
    Integer secondNumber;
    String mathOperation;
    String [] operand = {"+", "-", "*", "/"};

    RomanHandler romanHandler = new RomanHandler();
    public String calculate(String logicExp) {
        String[] exp = logicExp.split("\\s");
        formatTest(exp);
        mathOperation = exp[1];
        if (romanHandler.testNum(exp)){
            return computeRom(exp);
        }else {
            if (arabTest(exp)) {
                return String.valueOf(computeArab());
            }
        }
        return null;
    }
    /** Метод проводящий вычисления в римской стсьеме счисления */
    private String computeRom(String[] exp) {
        exp [0] = romanHandler.romToArab(exp [0]);
        exp [2] = romanHandler.romToArab(exp [2]);
        if (arabTest(exp)){
//            System.out.println("Результат: " + computeArab());
            try {
                return romanHandler.arabToRom(computeArab());
            } catch (ArithmeticException e){
                System.err.println("В римской системе счисления нет отрицательных чисел");
                System.exit(0);
            }

        }
        return null;
    }
    /** Метод проводящий вычисления в арабской системе счисления */
    private Integer computeArab() {
        return switch (mathOperation) {
            case "/" -> firstNumber / secondNumber;
            case "*" -> firstNumber * secondNumber;
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            default -> null;
        };
    }
    /** Проверка введенных чисел согласно условиям задачи */
    private boolean arabTest(String[] exp) {
        try {
            firstNumber = Integer.valueOf(exp[0]);
            secondNumber = Integer.valueOf(exp[2]);
        if (firstNumber > 10 | secondNumber > 10){
            throw new IndexOutOfBoundsException();
        } else {
            if ( firstNumber < 1 | secondNumber < 1){
                throw new IndexOutOfBoundsException();
            }
        }
        } catch (NumberFormatException e) {
            System.err.println("Неправильный формат значений");
            System.exit(0);
        } catch (IndexOutOfBoundsException e){
            System.err.println("Указанные значение(я) не удовлетворяют заданным рамкам (0; 10)");
            System.exit(0);
        }
        return true;
    }
    /** Проверка введенный строки на необходимым минимальным условиям 2 цифр и 1 математического
        и операнда между ними */
    private void formatTest(String[] exp) {
        try {
            if (exp.length != 3) {
                throw new Exception();
            }
            int count = 0;
            for (String s : operand) {
                if (exp[1].equals(s)) {
                    mathOperation = exp[1];
                    count++;
                }
            }
            if (count != 1) {
                throw new Exception();
            }
        }catch (Exception e){
            if (exp.length > 3){
                System.err.println("Формат не удовлетворяет требования - два значения и оператор");
            } else System.err.println("Строка не является математическим примером или указан неверный оператор");
            System.exit(0);
        }
    }
}
