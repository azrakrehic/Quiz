import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PlayQuiz {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Color quiz", GradingSystem.PARTIALLY);
        Question question1 = new Question("What are the colors of the rainbow?", 2);
        question1.addAnswer("a", "yellow", true);
        question1.addAnswer("b", "red", true);
        question1.addAnswer("c", "brown", false);
        question1.addAnswer("d", "orange", true);
        question1.addAnswer("e", "green", true);
        question1.addAnswer("f", "purple", true);
        question1.addAnswer("g", "blue", true);
        quiz.addQuestion(question1);

        Question question2 = new Question("What are the colors on a American flag?", 3);
        question2.addAnswer("a", "green", false);
        question2.addAnswer("b", "blue", true);
        question2.addAnswer("c", "black", false);
        question2.addAnswer("d", "white", true);
        question2.addAnswer("e", "red", true);
        quiz.addQuestion(question2);

        Question question3 = new Question("What color do you get by mixing blue and red", 2.5);
        question3.addAnswer("a", "purple", true);
        question3.addAnswer("b", "orange", false);
        question3.addAnswer("c", "green", false);
        quiz.addQuestion(question3);
        playQuiz(quiz);
    }

    public static void playQuiz(Quiz quiz) {
        try{
            System.out.println("Welcome to the " + quiz.getName() + " quiz. This quiz is being graded " + quiz.getGradingSystem().toString() + ". GOOD LUCK!");
            Map<Question, ArrayList<String>> answers = new HashMap<>();
            for (Question question: quiz.getQuestions()) {
                ArrayList<String> userAnswers = new ArrayList<>();
                System.out.println(question);
                String answer;
                System.out.println("Input answer/s you think are correct. (Press Enter for submitting).");
                Scanner input = new Scanner(System.in);
                for (;;) {
                    answer = input.nextLine();
                    if (answer.isEmpty()) break;
                    userAnswers.add(answer);
                }
                answers.put(question,userAnswers);
            }
            QuizResult quizResult = quiz.submitQuiz(answers);
            System.out.println("Quiz has been submited. Total points: " + quizResult.getTotalPoints() + "b. If you want to check all the correct answers input C, if you want to check the whole quiz imput Q.");
            Scanner input = new Scanner(System.in);
            if (input.nextLine().equals("Q")) System.out.println(quiz);
            else if (input.nextLine().equals("C")) {
                int count = 1;
                for (var question: quiz.getQuestions()) {
                    System.out.println(count+ ". Question: " +question.getText());
                    for (String id: question.getAnswers().keySet()) {
                        if (question.getAnswers().get(id).isCorrect()) System.out.println("\t" + id +") " + question.getAnswers().get(id).getAnswerText());
                    }
                    count = count + 1;
                }
            }
            System.out.println("Thanks for participating!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}