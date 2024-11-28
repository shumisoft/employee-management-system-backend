package com.shumisoft.employee_management_system.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class HomeController {

    @GetMapping()
    public String getMethodName() {
        return """
                <head>
                  <meta charset="UTF-8" />
                  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                  <title>API Server</title>
                  <style>
                    body {
                      font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
                      background-color: #f9f9f9;
                      color: #333;
                      display: flex;
                      align-items: center;
                      justify-content: center;
                      height: 100vh;
                      margin: 0;
                    }
                    .container {
                      text-align: center;
                    }
                    h1 {
                      font-size: 2.5em;
                      margin-bottom: 0.5em;
                      color: #2b7a78;
                    }
                    p {
                      font-size: 1.2em;
                      color: #555;
                    }
                    code {
                      background-color: #eee;
                      padding: 2px 6px;
                      border-radius: 4px;
                      font-family: monospace;
                    }
                  </style>
                </head>
                <body>
                  <div class="container">
                    <h1>👋 Welcome to EMS API Server</h1>
                    <p>This is the root route. The server is up and running.</p>
                    <p>Try accessing <code>/api/your-endpoint</code> to get started.</p>
                  </div>
                </body>
                """;

    }

}
