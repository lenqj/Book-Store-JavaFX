package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        return switch (table) {
            case BOOK -> "CREATE TABLE IF NOT EXISTS book (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  author varchar(500) NOT NULL," +
                    "  title varchar(500) NOT NULL," +
                    "  publishedDate datetime DEFAULT NULL," +
                    "  stock INT DEFAULT 0," +
                    "  price INT DEFAULT 0," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY book_id_unique (id)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case USER -> "CREATE TABLE IF NOT EXISTS user (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  username VARCHAR(200) NOT NULL," +
                    "  `password` VARCHAR(64) NOT NULL," +
                    "  money INT DEFAULT 0," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX user_id_unique (id ASC)," +
                    "  UNIQUE INDEX user_username_unique (username ASC)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case ROLE -> "CREATE TABLE IF NOT EXISTS role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX role_id_unique (id ASC)," +
                    "  UNIQUE INDEX role_role_unique (role ASC)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case RIGHT -> "CREATE TABLE IF NOT EXISTS `right` (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  `right` VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX right_id_unique (id ASC)," +
                    "  UNIQUE INDEX right_right_unique (`right` ASC)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case ROLE_RIGHT -> "CREATE TABLE IF NOT EXISTS role_right (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role_id INT NOT NULL," +
                    "  right_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX role_right_user_id_unique (id ASC)," +
                    "  INDEX role_right_role_id_idx (role_id ASC)," +
                    "  INDEX role_right_right_id_idx (right_id ASC)," +
                    "  CONSTRAINT role_right_role_id" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT role_right_right_id" +
                    "    FOREIGN KEY (right_id)" +
                    "    REFERENCES `right` (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case USER_ROLE -> "CREATE TABLE IF NOT EXISTS user_role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  user_id INT NOT NULL," +
                    "  role_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX user_role_user_id_unique (id ASC)," +
                    "  INDEX user_role_user_id_idx (user_id ASC)," +
                    "  INDEX user_role_role_id_idx (role_id ASC)," +
                    "  CONSTRAINT user_role_user_fk_id" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT user_role_role_fk_id" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case USER_BOOKS -> "CREATE TABLE IF NOT EXISTS user_books (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  user_id INT NOT NULL," +
                    "  book_id INT NOT NULL," +
                    "  date timestamp NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX user_books_user_id_unique (id ASC)," +
                    "  INDEX user_books_user_id_idx (user_id ASC)," +
                    "  INDEX user_books_book_id_idx (book_id ASC)," +
                    "  CONSTRAINT user_books_user_fk_id" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT user_books_book_fk_id" +
                    "    FOREIGN KEY (book_id)" +
                    "    REFERENCES book (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case USER_BOUGHT_BOOKS -> "CREATE TABLE IF NOT EXISTS user_bought_books (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  user_id INT NOT NULL," +
                    "  book_id INT NOT NULL," +
                    "  date timestamp NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX user_bought_books_user_id_unique (id ASC)," +
                    "  INDEX user_bought_books_user_id_idx (user_id ASC)," +
                    "  INDEX user_bought_books_book_id_idx (book_id ASC)," +
                    "  CONSTRAINT user_bought_books_user_fk_id" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT user_bought_books_book_fk_id" +
                    "    FOREIGN KEY (book_id)" +
                    "    REFERENCES book (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            default -> "";
        };
    }

}