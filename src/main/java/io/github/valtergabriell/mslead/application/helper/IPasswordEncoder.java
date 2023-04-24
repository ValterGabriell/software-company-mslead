package io.github.valtergabriell.mslead.application.helper;

@FunctionalInterface
public interface IPasswordEncoder {

    String encode(String password);
}
