import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz {
    private String name;
    private List<Question> questions;
    private GradingSystem gradingSystem;

    public Quiz(String name, GradingSystem gradingSystem) {
        this.name = name;
        questions = new ArrayList<>();
        this.gradingSystem = gradingSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public GradingSystem getGradingSystem() {
        return gradingSystem;
    }

    public void setGradingSystem(GradingSystem gradingSystem) {
        this.gradingSystem = gradingSystem;
    }

    public void addQuestion(Question question) {
        for (Question q : questions) {
            if (q.getText().equalsIgnoreCase(question.getText())) throw new IllegalArgumentException("Question with this text already exists!");
        }
        questions.add(question);
    }

    public QuizResult submitQuiz(Map<Question, ArrayList<String>> answeredQuestions) {
        QuizResult quizResult = new QuizResult(this);
        double totalPoints=0;
        Map<Question,Double> result = new HashMap<>();
        for (Question question: questions) {
            double questionPoints = 0;
            for (Question question1: answeredQuestions.keySet()) {
                if (question.equals(question1)) {
                    questionPoints = question.calculatePoints(answeredQuestions.get(question1), gradingSystem);
                    break;
                }
            }
            result.put(question, questionPoints);
            totalPoints = totalPoints + questionPoints;
        }
        quizResult.setTotalPoints(totalPoints);
        quizResult.setQuestionPoints(result);
        return quizResult;
    }


    @Override
    public String toString() {
        String quiz = "Quiz \"" + name + "" + "\"" + "is being graded " + gradingSystem.toString() + ".\nQuestions:";
        int i =1;
        for (var question: questions) {
            quiz = quiz + "\n" + i + ". " + question.getText() + "(" + question.getPoints() + "b)\n";
            int j = 1;
            for (String id : question.getAnswers().keySet()) {
                quiz = quiz + "\t" + id + ": "+ question.getAnswers().get(id).getAnswerText();
                if (question.getAnswers().get(id).isCorrect()) quiz = quiz + "(T)";
                if (i!=questions.size() || j!=question.getAnswers().size())  quiz = quiz + "\n";
                j = j + 1;
            }
            i = i + 1;
        }
        return quiz;
    }
}
