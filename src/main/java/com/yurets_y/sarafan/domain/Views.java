package com.yurets_y.sarafan.domain;

/*Интерфейс-маркер для аннотации JsonView
* JsonView(Views.Id.class) - сериализация в Json всех полей, отмеченных аннотацией.
* JsonView(Views.IdName.class) - сериализация в Json всех полей, отмеченных аннотацией, а также родительской аннотацией Id
* */
public final class Views {
    public interface Id{

    }
    public interface IdName extends Id{

    }
    public interface FullMessage extends IdName{

    }
}
