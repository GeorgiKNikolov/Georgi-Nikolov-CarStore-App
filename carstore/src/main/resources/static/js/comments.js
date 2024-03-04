const id = document.getElementById('id').value
const commentForm = document.getElementById('commentForm')
commentForm.addEventListener("submit", handleFormSubmission)

const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

const commentContainer = document.getElementById('commentsCtnr')

async function handleFormSubmission(event) {
    event.preventDefault()

    const messageVal = document.getElementById('message').value

    fetch(`http://localhost:8080/api/${id}/comments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            message: messageVal
        })
    }).then(res => res.json())
      .then(data => {
        document.getElementById('message').value = ""
        commentContainer.innerHTML += commentAsHtml(data)
      })
}

function commentAsHtml(comment) {
    let commentHtml = '<div>\n'
    commentHtml += `<h4>${comment.userName}</h4>\n`
    commentHtml += `<p>${comment.comment}</p>\n`
    commentHtml += '</div>\n'

    return commentHtml
}

fetch(`http://localhost:8080/api/${id}/comments`, {
    headers: {
        "Accept": "application/json"
    }
}).then(res => res.json())
    .then(data => {
        for(let comment of data) {
            commentContainer.innerHTML += commentAsHtml(comment)
        }
    })