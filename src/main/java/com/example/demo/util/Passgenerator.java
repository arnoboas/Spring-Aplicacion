package com.example.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Passgenerator {
	
	public static void main(String ...args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
       
        //El String que mandamos al metodo encode es el password que queremos encriptar.
        System.out.println(bCryptPasswordEncoder.encode("1234"));
        /*
         * Resultado: $2a$04$n6WIRDQlIByVFi.5rtQwEOTAzpzLPzIIG/O6quaxRKY2LlIHG8uty
         con 1234 clave encriptada: $2a$04$czNBRKmdPL7uOZ9ry.w9mu6h7ANkqSQQZvBhparl/BTxKzP4WV0tu
         */
	}

}
