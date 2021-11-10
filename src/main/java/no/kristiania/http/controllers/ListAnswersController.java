package no.kristiania.http.controllers;

import no.kristiania.db.answer.Answer;
import no.kristiania.db.answer.AnswerDao;
import no.kristiania.http.HttpMessage;
import no.kristiania.db.option.Option;
import no.kristiania.db.option.OptionDao;
import no.kristiania.db.person.Person;
import no.kristiania.db.person.PersonDao;
import no.kristiania.db.question.Question;
import no.kristiania.db.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class ListAnswersController implements HttpController {

    private AnswerDao answerDao;
    private QuestionDao questionDao;
    private OptionDao optionDao;
    private PersonDao personDao;

    public ListAnswersController(AnswerDao answerDao, QuestionDao questionDao, OptionDao optionDao, PersonDao personDao){
        this.answerDao = answerDao;
        this.questionDao = questionDao;
        this.optionDao = optionDao;
        this.personDao = personDao;
    }


    @Override
    public String getPath() {
        return "/api/answer";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String response = "";

        for(Answer answer : answerDao.listAllAnswers()){
            for(Question question : questionDao.listAllQuestion()){
                for(Option option : optionDao.listAllOption()){
                    for(Person person : personDao.listAll()){

                        if(answer.getOptionId() == option.getOptionId() && answer.getQuestionId() == question.getQuestionId()){
                            response += "User: " + person.getFirstName() + person.getLastName() + "Question: " + question.getQuestionTitle();
                        }

                        response += "Hei";
                    }
                }
            }
        }





        return new HttpMessage("Http/1.1 200 OK", response);
    }
}
