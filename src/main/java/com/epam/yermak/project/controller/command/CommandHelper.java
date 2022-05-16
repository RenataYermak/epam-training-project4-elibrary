package com.epam.yermak.project.controller.command;

import com.epam.yermak.project.controller.command.book.*;
import com.epam.yermak.project.controller.command.user.*;

import java.util.EnumMap;

public class CommandHelper {
    private static CommandHelper instance;
    private final EnumMap<CommandName, Command> commands = new EnumMap<>(CommandName.class);

    private CommandHelper() {
        commands.put(CommandName.ADD_BOOK, new AddBookCommand());
        commands.put(CommandName.EDIT_BOOK, new EditBookCommand());
        commands.put(CommandName.FIND_BOOK, new FindBookCommand());
        commands.put(CommandName.FIND_BOOKS, new FindAllBooksCommand());
        commands.put(CommandName.DELETE_BOOK, new DeleteBookCommand());
        commands.put(CommandName.BOOK_SEARCH, new BookSearchCommand());
        commands.put(CommandName.FIND_ORDERS_BY_STATUS, new FindOrdersByStatus());
        commands.put(CommandName.FIND_ORDERS_BY_USER, new FindOrdersByUser());
        commands.put(CommandName.ORDER_BOOK, new OrderBookCommand());
        commands.put(CommandName.RESERVE_BOOK, new ReserveBookCommand());
        commands.put(CommandName.RETURN_BOOK, new ReturnBookCommand());
        commands.put(CommandName.REJECT_ORDER, new RejectOrderCommand());

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

    public  Command getCommand(String commandName) {
        CommandName commandType;
        try {
            commandType = CommandName.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = CommandName.SIGN_IN;
        }
        return commands.get(commandType);
    }
//    private static CommandHelper INSTANCE = new CommandHelper();
//    private final EnumMap<CommandName, Command> commands;
//
//    private CommandHelper() {
//        this.commands = new EnumMap<>(CommandName.class);
//        this.commands.put(CommandName.ADD_BOOK, new AddBookCommand());
//        this.commands.put(CommandName.EDIT_BOOK, new EditBookCommand());
//        this.commands.put(CommandName.FIND_BOOK, new FindBookCommand());
//        this.commands.put(CommandName.FIND_BOOKS, new FindAllBooksCommand());
//        this.commands.put(CommandName.DELETE_BOOK, new DeleteBookCommand());
//        this.commands.put(CommandName.BOOK_SEARCH, new BookSearchCommand());
//        this.commands.put(CommandName.FIND_ORDERS_BY_STATUS, new FindOrdersByStatus());
//        this.commands.put(CommandName.FIND_ORDERS_BY_USER, new FindOrdersByUser());
//        this.commands.put(CommandName.ORDER_BOOK, new OrderBookCommand());
//        this.commands.put(CommandName.RESERVE_BOOK, new ReserveBookCommand());
//        this.commands.put(CommandName.RETURN_BOOK, new ReturnBookCommand());
//        this.commands.put(CommandName.REJECT_ORDER, new RejectOrderCommand());
//
//        this.commands.put(CommandName.SIGN_IN, new SignInCommand());
//        this.commands.put(CommandName.SIGN_OUT, new SignOutCommand());
//        this.commands.put(CommandName.REGISTRATION, new RegistrationCommand());
//        this.commands.put(CommandName.EDIT_USER, new EditUserCommand());
//        this.commands.put(CommandName.FIND_USER, new FindUserCommand());
//        this.commands.put(CommandName.FIND_USERS, new FindAllUsersCommand());
//        this.commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
//        this.commands.put(CommandName.DEACTIVATE_USER, new DeactivateUserCommand());
//        this.commands.put(CommandName.USER_SEARCH, new UserSearchCommand());
//        this.commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
//    }
//
//    private static CommandHelper getInstance() {
//        if (INSTANCE == null) {
//            INSTANCE = new CommandHelper();
//        }
//        return INSTANCE;
//    }
//
//    public static Command getCommand(HttpServletRequest request) {
//        String commandName = request.getParameter(RequestParam.COMMAND);
//        return getInstance().commands.get(CommandName.valueOf(commandName.toUpperCase()));
//    }
}
