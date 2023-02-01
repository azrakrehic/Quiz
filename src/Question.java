import java.util.*;

public class Question {
    private String text;
    private double points;
    private Map<String,Answer > answers;

    public Question(String text, double points) {
        this.text = text;
        this.points = points;
        answers=new HashMap<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public Map<String,Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String,Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(String id, String text, boolean correct) {
        if (answers.containsKey(id)) throw new IllegalArgumentException("Answer with this id already exists!");
        answers.put(id,new Answer(text,correct));
    }

    public void deleteAnswer(String id){
        if(!answers.containsKey(id)) throw new IllegalArgumentException("Answer with this id does not exist!");
        answers.remove(id);
    }
    public List<Answer> getAnswersAsList() {
        return new ArrayList<>(answers.values());
    }

    public double calculatePoints(List<String> ids, GradingSystem gradingSystem) {
        Set<String> answerSet = new HashSet<>();
        for (String id : ids) {
            if(!answers.containsKey(id)) throw new IllegalArgumentException("There is no answer with this id!");
            if(!answerSet.add(id)) throw new IllegalArgumentException("There is duplicate answers in the list od ids!");
        }
        if(ids.isEmpty()) return 0;
        int totalCorrectAnswers=0, correctAnswers = 0 , wrongAnswers = 0;
        for (Answer answer : answers.values()) {
            if (answer.isCorrect()) totalCorrectAnswers=totalCorrectAnswers+1;
        }
        for (String id: ids) {
            if (answers.get(id).isCorrect()) correctAnswers=correctAnswers+1;
            else wrongAnswers=wrongAnswers+1;
        }
        if (gradingSystem.equals(GradingSystem.BINARY)){
            if (correctAnswers==totalCorrectAnswers && wrongAnswers==0) return points;
            return 0;
        }

        else if (gradingSystem.equals(GradingSystem.PARTIALLY)) {
            if (wrongAnswers!=0) return 0;
            else if (totalCorrectAnswers==correctAnswers) return points;
            return (points/answers.size()) * correctAnswers;
        }

        if(correctAnswers==totalCorrectAnswers && wrongAnswers==0) return points;
        if (wrongAnswers==0) return (points/answers.size()) * correctAnswers;
        return -points/2;
    }

    @Override
    public String toString() {
         String question = text + "(" + points + "b)\n";
         int i = 1;
         for (String id : answers.keySet()) {
             question = question + "\t" + id + ": " + answers.get(id).getAnswerText();
             if (i!=answers.size()) question = question + "\n";
             i++;
         }
         return question;
    }
}
