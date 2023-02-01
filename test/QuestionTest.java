import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    @Test
    public void testConstructor() {
        Question question = new Question("What is the default scope in Java?", 2);
        assertAll(
                () -> assertEquals("What is the default scope in Java?", question.getText()),
                () -> assertEquals(2, question.getPoints()),
                () -> assertTrue(question.getAnswers().isEmpty())
        );
    }

    @Test
    public void testSetters() {
        Question question = new Question("What is the default scope in Java?",2);
        question.setPoints(2.5);
        question.setText("What is not the default scope in Java?");
        assertAll(
                () -> assertEquals("What is not the default scope in Java?", question.getText()),
                () -> assertEquals(2.5, question.getPoints()),
                () -> assertTrue(question.getAnswers().isEmpty())
        );
    }

    @Test
    public void testAddAnswer1() {
        Question question = new Question("What is the default scope in Java?", 2);
        question.addAnswer("a", "package", true);
        question.addAnswer("b", "private", false);
        question.addAnswer("c", "protected", false);
        question.addAnswer("d", "public", false);
        assertEquals(4, question.getAnswers().size());
    }

    @Test
    public void testAddAnswer2() {
        Question question = new Question("What is the default scope in Java?", 2);
        question.addAnswer("a", "package", true);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> question.addAnswer("a", "private", false));
        assertAll(
                () -> assertEquals("Answer with this id already exists!", exception.getMessage()),
                () -> assertEquals(1, question.getAnswers().size())
        );
    }

    @Test
    public void testDeleteAnswer1() {
        Question question = new Question("What is the default scope in Java?", 2);
        question.addAnswer("a", "package", true);
        question.deleteAnswer("a");
        assertTrue(question.getAnswers().isEmpty());
    }

    @Test
    public void testDeleteAnswer2() {
        Question question = new Question("What is the default scope in Java?", 2);
        question.addAnswer("a", "package", true);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> question.deleteAnswer("b"));
        assertAll(
                () -> assertEquals("Answer with this id does not exist!", exception.getMessage()),
                () -> assertEquals(1, question.getAnswers().size())
        );
    }
    @Test
    public void testGetAnswersAsList() {
        Question question = new Question("What is the default scope in Java?", 2);
        question.addAnswer("a", "package", true);
        question.addAnswer("b", "private", false);
        question.addAnswer("c", "protected", false);
        question.addAnswer("d", "public", false);
        List<Answer> answers = question.getAnswersAsList();
        assertAll(
                () -> assertEquals(4, answers.size()),
                () -> assertTrue(answers.contains(new Answer("package", true))),
                () -> assertTrue(answers.contains(new Answer("private", false))),
                () -> assertTrue(answers.contains(new Answer("protected", false))),
                () -> assertTrue(answers.contains(new Answer("public", false))),
                () -> assertFalse(answers.contains(new Answer("private", true)))
        );
    }

    @Test
    public void testCalculatePoints1() {
        Question question = new Question("What is the default scope in Java?", 2);
        question.addAnswer("a", "package", true);
        question.addAnswer("b", "private", false);
        question.addAnswer("c", "protected", false);
        question.addAnswer("d", "public", false);
        double points1 = question.calculatePoints(List.of("a"), GradingSystem.BINARY);
        question.setPoints(3);
        double points2 = question.calculatePoints(List.of("a"), GradingSystem.BINARY);
        assertAll(
                () -> assertEquals(2, points1),
                () -> assertEquals(3, points2)
        );
    }

    @Test
    public void testCalculatePoints2() {
        Question question = new Question("What is the default scope in Java?", 2);
        question.addAnswer("a", "package", true);
        question.addAnswer("b", "private", false);
        question.addAnswer("c", "protected", false);
        question.addAnswer("d", "public", false);
        double points1 = question.calculatePoints(List.of("b"), GradingSystem.BINARY);
        double points2 = question.calculatePoints(List.of("a"), GradingSystem.BINARY);
        assertAll(
                () -> assertEquals(0, points1),
                () -> assertEquals(2, points2)
        );
    }

    @Test
    public void testCalculatePoints3() {
        Question question = new Question("What is the default scope in Java?", 2);
        question.addAnswer("a", "package", true);
        question.addAnswer("b", "private", false);
        question.addAnswer("c", "protected", false);
        question.addAnswer("d", "public", false);
        double points = question.calculatePoints(List.of("a", "c"), GradingSystem.BINARY);
        assertEquals(0, points);
    }

    @Test
    public void testCalculatePoints4() {
        Question question = new Question("What are the colors on the traffic light?", 2);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        double points1 = question.calculatePoints(List.of("a"), GradingSystem.BINARY);
        double points2 = question.calculatePoints(List.of("a", "b"), GradingSystem.BINARY);
        double points3 = question.calculatePoints(List.of("a", "b", "c"), GradingSystem.BINARY);
        assertAll(
                () -> assertEquals(0, points1),
                () -> assertEquals(0, points2),
                () -> assertEquals(2, points3)
        );
    }

    @Test
    public void testCalculatePoints5() {
        Question question = new Question("What are the colors on the traffic light?", 2);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        double points = question.calculatePoints(List.of(), GradingSystem.BINARY);
        assertEquals(0, points);
    }

    @Test
    public void testCalculatePoints6() {
        Question question = new Question("What are the colors on thw traffic light?", 3);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        double points1 = question.calculatePoints(List.of("a"), GradingSystem.PARTIALLY);
        double points2 = question.calculatePoints(List.of("a", "b"), GradingSystem.PARTIALLY);
        double points3 = question.calculatePoints(List.of("a", "b", "c"), GradingSystem.PARTIALLY);
        assertAll(
                () -> assertEquals(1, points1),
                () -> assertEquals(2, points2),
                () -> assertEquals(3, points3)
        );
    }

    @Test
    public void testCalculatePoints7() {
        Question question = new Question("What are the colors on thw traffic light?", 4);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        question.addAnswer("d", "blue", false);
        double points1 = question.calculatePoints(List.of("a"), GradingSystem.PARTIALLY);
        double points2 = question.calculatePoints(List.of("a", "b"), GradingSystem.PARTIALLY);
        double points3 = question.calculatePoints(List.of("a", "b", "d"), GradingSystem.PARTIALLY);
        double points4 = question.calculatePoints(List.of("a", "d"), GradingSystem.PARTIALLY);
        double points5 = question.calculatePoints(List.of( "d"), GradingSystem.PARTIALLY);

        assertAll(
                () -> assertEquals(1, points1),
                () -> assertEquals(2, points2),
                () -> assertEquals(0, points3),
                () -> assertEquals(0,points4),
                () -> assertEquals(0,points5)
        );
    }
    @Test
    public void testCalculatePoints8() {
        Question question = new Question("What are the colors on thw traffic light?", 4);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        question.addAnswer("d", "blue", false);
        double points1 = question.calculatePoints(List.of(), GradingSystem.PARTIALLY);
        double points2 = question.calculatePoints(List.of("a", "b", "c"), GradingSystem.PARTIALLY);
        double points3 = question.calculatePoints(List.of("a", "b", "c", "d"), GradingSystem.PARTIALLY);
        assertAll(
                () -> assertEquals(0, points1),
                () -> assertEquals(4, points2),
                () -> assertEquals(0, points3)
        );
    }

    @Test
    public void testCalculatePoints9() {
        Question question = new Question("What are the colors on thw traffic light?", 4);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        question.addAnswer("d", "blue", false);
        double points1 = question.calculatePoints(List.of(), GradingSystem.PARTIALLY_WITH_NEGATIVE_POINTS);
        double points2 = question.calculatePoints(List.of("a", "b", "c"), GradingSystem.PARTIALLY_WITH_NEGATIVE_POINTS);
        double points3 = question.calculatePoints(List.of("a", "b", "c", "d"), GradingSystem.PARTIALLY_WITH_NEGATIVE_POINTS);
        assertAll(
                () -> assertEquals(0, points1),
                () -> assertEquals(4, points2),
                () -> assertEquals(-2, points3)
        );
    }

    @Test
    public void testCalculatePoints10() {
        Question question = new Question("What are the colors on thw traffic light?", 4);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        question.addAnswer("d", "blue", false);
        double points1 = question.calculatePoints(List.of("a"), GradingSystem.PARTIALLY_WITH_NEGATIVE_POINTS);
        double points2 = question.calculatePoints(List.of("a", "b"), GradingSystem.PARTIALLY_WITH_NEGATIVE_POINTS);
        double points3 = question.calculatePoints(List.of("a", "b", "d"), GradingSystem.PARTIALLY_WITH_NEGATIVE_POINTS);
        double points4 = question.calculatePoints(List.of("d"), GradingSystem.PARTIALLY_WITH_NEGATIVE_POINTS);
        double points5 = question.calculatePoints(List.of("a", "b", "c"), GradingSystem.PARTIALLY_WITH_NEGATIVE_POINTS);
        assertAll(
                () -> assertEquals(1, points1),
                () -> assertEquals(2, points2),
                () -> assertEquals(-2, points3),
                () -> assertEquals(-2, points4),
                () -> assertEquals(4, points5)
        );
    }

    @Test
    public void testingValidationOfAnswer1() {
        Question question = new Question("What are the colors on thw traffic light?", 4);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        question.addAnswer("d", "blue", false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> question.calculatePoints(List.of("e"), GradingSystem.BINARY));
        assertEquals("There is no answer with this id!", exception.getMessage());
    }

    @Test
    public void testingValidationOfAnswer2() {
        Question question = new Question("What are the colors on thw traffic light?", 4);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);
        question.addAnswer("d", "blue", false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> question.calculatePoints(List.of("a", "b", "a"), GradingSystem.BINARY));
        assertEquals("There is duplicate answers in the list od ids!", exception.getMessage());
    }

    @Test
    public void toStringTest() {
        Question question = new Question("What are the colors on thw traffic light?", 4);
        question.addAnswer("a", "red", true);
        question.addAnswer("b", "green", true);
        question.addAnswer("c", "yellow", true);

        String result = "What are the colors on thw traffic light?(4.0b)\n\ta: red\n\tb: green\n\tc: yellow";
        String actualResult = question.toString();
        question.addAnswer("d", "blue", false);

        assertAll(
                () -> assertEquals(result,actualResult),
                () -> assertEquals(result+"\n\td: blue", question.toString())
        );
    }

}