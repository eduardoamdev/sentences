public class Templates {
    public String buildTemplate(String template) {
        String bodyContent;
        String javascriptContent;
        if ("main".equals(template)){
            bodyContent = main;
            javascriptContent = "";
        } else if ("newSentence".equals(template)) {
            bodyContent = newSentence;
            javascriptContent = new HtmlScripts().newSentence;
        } else if ("sentences".equals(template)) {
            bodyContent = sentences;
            javascriptContent = new HtmlScripts().sentences;
        }  else {
            bodyContent = "";
            javascriptContent = "";
        }
        return "<html>\n" +
                "<head>\n" +
                "   <title>Sentences</title>\n" +
                "   <style>" + new Styles().styles + "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                navbar +
                bodyContent +
                javascriptContent +
                "</body>\n" +
                "</html>";
    }

    String navbar = "<nav class='pl-6 pt-4 pb-4 bg-blue-green'>" +
            "<a href='/home' class='fs-2_5'>Home</a>" +
            "<a href='/newSentence' class='fs-2_5 ml-6'>New sentence</a>" +
            "<a href='/sentences' class='fs-2_5 ml-6'>Sentences</a>" +
            "</nav>";

    String main = "<h1 class='flex-center mt-20 fs-4'>Welcome to Sentences CRUD!</h1>\n";

    String newSentence = "<div class='flex-center-column'>" +
            "<h2 class='flex-center mt-15 fs-3_5'>New sentence</h2>" +
            "<input id='newSentenceInput' type='text' class='mt-10 p-2 fs-2_2' />" +
            "<button id='newSentenceButton' class='mt-7 p-1 fs-2_2'>Create</button>" +
            "<p id='newSentenceMessage'></p>" +
            "</div>";

    String sentences = "<div class='flex-center-column mb-10'>" +
            "<h2 class='flex-center mt-15 fs-3_5'>Five sentences</h2>" +
            "<div id='sentencesContainer' class='mt-5 fs-2_5 flex-center-column'>" +
            "</div>" +
            "<p id='sentencesMessage' class='fs-2_5'>Loading...</p>" +
            "</div>";
}
