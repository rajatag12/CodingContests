package main;

import net.egork.chelper.tester.State;
import net.egork.chelper.tester.Verdict;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public abstract class TaskInteractor {
    public Verdict interact(InputStream input, InputStream solutionOutput, OutputStream solutionInput, State<Boolean> state) {
        Scanner s = new Scanner(solutionOutput);
        PrintWriter writer = new PrintWriter(solutionInput);

        setInitialState(new Scanner(input), writer);

        Verdict verdict = Verdict.UNDECIDED;
        while (state.getState()) {
            try {
                if (solutionOutput.available() == 0) {
                    continue;
                }
                String q = s.nextLine();
                String rep = reply(q);
                boolean done = false;
                if (rep.startsWith("_")) {
                    writer.println(rep.substring(1));
                    done = true;
                } else {
                    writer.println(rep);
                }
                writer.flush();
                if (done) {
                    verdict = checkAnswer(rep.substring(1));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return verdict;
    }

    protected abstract void setInitialState(Scanner in, PrintWriter out);

    protected abstract String reply(String ask);
    /*{
        return ask.equals("Haha") ? "_Y" : "2";
    }*/

    protected abstract Verdict checkAnswer(String result); 
    /*{
        return Verdict.OK;
    }*/
}
