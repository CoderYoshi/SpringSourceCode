package dream.service;

import dream.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookService {

    //@Autowired
    //@Qualifier("bookDao")
    @Inject
    private BookDao bookDao;

    public void printDao(){
        System.out.println(bookDao);
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao=" + bookDao +
                '}';
    }
}
