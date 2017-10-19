/*
 * Copyright (C) 2013-2017 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.tourreservation.tourreserve.common.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.SequenceInputStream;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

public class MessageKeysGen {
    public static void main(String[] args) throws IOException {
        // message properties file

        InputStream applicationMessagesInputStream = new ClassPathResource("i18n/application-messages_en.properties")
                .getInputStream();
        InputStream validationMessagesInputStream = new ClassPathResource("ValidationMessages_en.properties")
                .getInputStream();
        SequenceInputStream inputStream = new SequenceInputStream(applicationMessagesInputStream, validationMessagesInputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Class<?> targetClazz = MessageKeys.class;
        File output = new File("src/test/java/" + targetClazz.getName()
                .replaceAll(Pattern.quote("."), "/") + ".java");
        System.out.println("write " + output.getAbsolutePath());
        PrintWriter pw = new PrintWriter(FileUtils.openOutputStream(output));

        try {
            pw.println("/*");
            pw.println(" * Copyright (C) 2013-2017 NTT DATA Corporation");
            pw.println(" *");
            pw.println(
                    " * Licensed under the Apache License, Version 2.0 (the \"License\");");
            pw.println(
                    " * you may not use this file except in compliance with the License.");
            pw.println(" * You may obtain a copy of the License at");
            pw.println(" *");
            pw.println(" *     http://www.apache.org/licenses/LICENSE-2.0");
            pw.println(" *");
            pw.println(
                    " * Unless required by applicable law or agreed to in writing, software");
            pw.println(
                    " * distributed under the License is distributed on an \"AS IS\" BASIS,");
            pw.println(" * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,");
            pw.println(
                    " * either express or implied. See the License for the specific language");
            pw.println(
                    " * governing permissions and limitations under the License.");
            pw.println(" */");
            pw.println("package " + targetClazz.getPackage().getName() + ";");
            pw.println("/**");
            pw.println(" * Message Id");
            pw.println(" */");
            pw.println("public class " + targetClazz.getSimpleName() + " {");

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] vals = line.split("=", 2);
                if (vals.length > 1) {
                    String key = vals[0].trim();
                    String value = vals[1].trim();
                    pw.println("    /** " + key + "=" + value + " */");
                    pw.println("    public static final String " + key
                            .toUpperCase().replaceAll(Pattern.quote("."), "_")
                            .replaceAll(Pattern.quote("-"), "_") + " = \"" + key
                            + "\";");
                }
            }
            pw.println("}");
            pw.flush();
        } finally {
            IOUtils.closeQuietly(applicationMessagesInputStream);
            IOUtils.closeQuietly(validationMessagesInputStream);
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(pw);
        }
    }
}
