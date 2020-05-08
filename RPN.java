import java.util.Scanner;

class StackNode {
    public StackNode underneath;
    public double data;

    public StackNode(double data, StackNode underneath) {
        this.data = data;
        this.underneath = underneath;
    }
}

class RPN {
    private String command;
    private StackNode top = null;

    public void into(double new_data) {
        StackNode new_node = new StackNode(new_data, this.top);
        this.top = new_node;
    }

    public double outof() {
        double top_data = this.top.data;
        this.top = this.top.underneath;
        return top_data;
    }

    public RPN(String command) {
        this.command = command;
    }

    public double get() {
        for(int i = 0; i < this.command.length(); ++i) {
            if (!Character.isDigit(this.command.charAt(i))) {
                double a;
                double b;
                if (this.command.charAt(i) == '+') {
                    b = this.outof();
                    a = this.outof();
                    this.into(a + b);
                } else if (this.command.charAt(i) == '-') {
                    b = this.outof();
                    a = this.outof();
                    this.into(a - b);
                } else if (this.command.charAt(i) == '*') {
                    b = this.outof();
                    a = this.outof();
                    this.into(a * b);
                } else if (this.command.charAt(i) == '/') {
                    b = this.outof();
                    a = this.outof();
                    this.into(a / b);
                } else if (this.command.charAt(i) == '^') {
                    b = this.outof();
                    a = this.outof();
                    this.into(Math.pow(a, b));
                } else if (this.command.charAt(i) != ' ') {
                    throw new IllegalArgumentException();
                }
            } else {
                String temp = "";

                for(int j = 0; j < 100 && (Character.isDigit(this.command.charAt(i)) || this.command.charAt(i) == '.'); ++i)
                {
                    temp = temp + String.valueOf(this.command.charAt(i));
                    ++j;
                }

                double number = Double.parseDouble(temp);
                this.into(number);
            }
        }

        double val = this.outof();
        if (this.top != null) {
            throw new IllegalArgumentException();
        } else {
            return val;
        }
    }

    public static void main(String[] args) {
        while(true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter RPN expression or \"quit\".");
            String line = in.nextLine();
            if (line.equals("quit")) {
                return;
            }

            RPN calc = new RPN(line);
            System.out.printf("Answer is %f\n", calc.get());
        }
    }
}
