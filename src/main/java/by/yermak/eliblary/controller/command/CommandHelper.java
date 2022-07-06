package by.yermak.eliblary.controller.command;

import by.yermak.eliblary.controller.command.book.*;
import by.yermak.eliblary.controller.command.page.AboutLibraryPageCommand;
import by.yermak.eliblary.controller.command.page.AddBookPageCommand;
import by.yermak.eliblary.controller.command.page.BookViewPageCommand;
import by.yermak.eliblary.controller.command.page.RegisterPageCommand;
import by.yermak.eliblary.controller.command.user.*;


import java.util.EnumMap;

public class CommandHelper {
    private static CommandHelper instance;
    private final EnumMap<CommandName, Command> commands = new EnumMap<>(CommandName.class);

    private CommandHelper() {
        commands.put(CommandName.ADD_BOOK, new AddBookCommand());
        commands.put(CommandName.EDIT_BOOK, new EditBookCommand());
        commands.put(CommandName.EDIT_BOOK_PICTURE, new EditBookPictureCommand());
        commands.put(CommandName.FIND_BOOK, new FindBookCommand());
        commands.put(CommandName.FIND_BOOKS, new FindAllBooksCommand());
        commands.put(CommandName.DELETE_BOOK, new DeleteBookCommand());
        commands.put(CommandName.BOOK_SEARCH, new BookSearchCommand());
        commands.put(CommandName.FIND_ORDERS_BY_STATUS, new FindOrdersByStatusCommand());
        commands.put(CommandName.FIND_ORDERS_BY_USER, new FindOrdersByUserCommand());
        commands.put(CommandName.ORDER_BOOK, new OrderBookCommand());
        commands.put(CommandName.RESERVE_BOOK, new ReserveBookCommand());
        commands.put(CommandName.RETURN_BOOK, new ReturnBookCommand());
        commands.put(CommandName.REJECT_ORDER, new RejectOrderCommand());
        commands.put(CommandName.ABOUT_LIBRARY_PAGE, new AboutLibraryPageCommand());
        commands.put(CommandName.ADD_BOOK_PAGE, new AddBookPageCommand());
        commands.put(CommandName.REGISTER_PAGE, new RegisterPageCommand());
        commands.put(CommandName.BOOK_VIEW_PAGE, new BookViewPageCommand());

        commands.put(CommandName.SIGN_IN, new SignInCommand());
        commands.put(CommandName.SIGN_OUT, new SignOutCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.EDIT_USER, new EditUserCommand());
        commands.put(CommandName.FIND_USER, new FindUserCommand());
        commands.put(CommandName.FIND_USERS, new FindAllUsersCommand());
        commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandName.DEACTIVATE_USER, new DeactivateUserCommand());
        commands.put(CommandName.USER_SEARCH, new UserSearchCommand());
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
    }

    public static CommandHelper getInstance() {
        if (instance == null) {
            instance = new CommandHelper();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        CommandName commandType;
        try {
            commandType = CommandName.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = CommandName.SIGN_IN;
        }
        return commands.get(commandType);
    }
}
