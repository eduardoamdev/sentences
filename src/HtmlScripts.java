public class HtmlScripts {
    String newSentence = "<script>" +
            "const newSentenceInput = document.getElementById('newSentenceInput');" +
            "const newSentenceButton = document.getElementById('newSentenceButton');" +
            "const newSentenceMessage = document.getElementById('newSentenceMessage');" +
            "newSentenceButton.addEventListener('click', () => {" +
            "    fetch('/newSentence', {" +
            "        method: 'POST'," +
            "        headers: {" +
            "            'Content-Type': 'application/json'" +
            "        }," +
            "        body: JSON.stringify({ sentence: newSentenceInput.value })" +
            "    })" +
            "    .then(response => response.json())" +
            "    .then(data => {" +
            "        if(data.severity === 'ERROR'){" +
            "            throw new Error(data.message) " +
            "        }" +
            "        newSentenceMessage.classList.remove('fc-red');" +
            "        newSentenceMessage.classList.add(\"fs-2_5\", \"mt-7\", \"fc-green\");" +
            "        newSentenceMessage.innerText = data.message;" +
            "        newSentenceInput.value = ''" +
            "    })" +
            "    .catch(error => {" +
            "        newSentenceMessage.classList.remove('fc-green');" +
            "        newSentenceMessage.classList.add(\"fs-2_5\", \"mt-7\", \"fc-red\");" +
            "        newSentenceMessage.innerText = error.message;" +
            "    })" +
            "});" +
            "</script>";

    String sentences = "<script>" +
            "const sentencesContainer = document.getElementById('sentencesContainer');" +
            "const sentencesMessage = document.getElementById('sentencesMessage');" +
            "fetch('/fiveSentences', {" +
            "    method: 'GET'," +
            "    headers: {" +
            "        'Content-Type': 'application/json'" +
            "        }," +
            "    })" +
            "    .then(response => response.json())" +
            "    .then(data => {" +
            "        if(data.severity === 'ERROR'){" +
            "            throw new Error(data.message) " +
            "        }" +
            "        for(let i = 0; i < data.info.length; i++) {" +
            "            const span = document.createElement('span');" +
            "            span.textContent = data.info[i];" +
            "            span.classList.add(\"mb-3\");" +
            "            sentencesContainer.appendChild(span);" +
            "        };" +
            "        sentencesMessage.innerText = ''" +
            "    })" +
            "    .catch(error => {" +
            "        sentencesMessage.classList.add(\"fc-red\");" +
            "        sentencesMessage.innerText = 'Server error';" +
            "})" +
            "</script>";
}
