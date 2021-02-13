package com.steamnonesteam.authorization;


import io.javalin.core.security.Role;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public enum Roles implements Role {
    ADMIN,
    USER;

}
