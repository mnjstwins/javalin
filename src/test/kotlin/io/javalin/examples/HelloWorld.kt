/*
 * Javalin - https://javalin.io
 * Copyright 2017 David Åse
 * Licensed under Apache 2.0: https://github.com/tipsy/javalin/blob/master/LICENSE
 */

package io.javalin.examples

import io.javalin.Javalin

fun main(args: Array<String>) {
    val app = Javalin.create().port(7000)
    app.get("/") { req, res -> res.body("Hello World") }
}
