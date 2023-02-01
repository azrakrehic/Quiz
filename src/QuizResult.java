import java.util.HashMap;
import java.util.Map;

public class QuizResult {
    private Quiz quiz;
    private double totalPoints;
    private Map<Question,Double> questionPoints;

    public QuizResult(Quiz quiz) {
        this.quiz = quiz;
        totalPoints=0;
        questionPoints=new HashMap<>();
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Map<Question, Double> getQuestionPoints() {
        return questionPoints;
    }

    public void setQuestionPoints(Map<Question, Double> questionPoints) {
        this.questionPoints = questionPoints;
    }

    @Override
    public String toString() {
        String quizResult = "On this " + quiz.getName() + " quiz you got " + totalPoints + " points in total. Points for individual questions:\n";
        int i = 1;
        for (Question question: questionPoints.keySet()) {
            quizResult = quizResult + question.getText() + " - " + questionPoints.get(question) + "b";
            if (i!=questionPoints.size()) quizResult = quizResult + "\n";
            i = i + 1;
        }
        return quizResult;
    }
}



