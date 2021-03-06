/*
 * Javalin - https://javalin.io
 * Copyright 2017 David Åse
 * Licensed under Apache 2.0: https://github.com/tipsy/javalin/blob/master/LICENSE
 */

package io.javalin.examples

import io.javalin.ApiBuilder.get
import io.javalin.ApiBuilder.path
import io.javalin.Javalin
import io.javalin.examples.HelloWorldAuth.MyRoles.*
import io.javalin.security.Role
import io.javalin.security.Role.roles

enum class MyRoles : Role {
    ROLE_ONE, ROLE_TWO, ROLE_THREE
}

fun main(args: Array<String>) {

    Javalin.create()
            .port(7070)
            .accessManager { handler, request, response, permittedRoles ->
                val userRole = request.queryParam("role")
                if (userRole != null && permittedRoles.contains(MyRoles.valueOf(userRole))) {
                    handler.handle(request, response)
                } else {
                    response.status(401).body("Unauthorized")
                }
            }
            .routes {
                get("/hello", { req, res -> res.body("Hello World 1") }, roles(ROLE_ONE))
                path("/api") {
                    get("/test", { req, res -> res.body("Hello World 2") }, roles(ROLE_TWO))
                    get("/tast", { req, res -> res.status(200).body("Hello world 3") }, roles(ROLE_THREE))
                    get("/hest", { req, res -> res.status(200).body("Hello World 4") }, roles(ROLE_ONE, ROLE_TWO))
                    get("/hast", { req, res -> res.status(200).body("Hello World 5").header("test", "tast") }, roles(ROLE_ONE, ROLE_THREE))
                }
            }
}

