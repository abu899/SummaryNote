# Git Commit Msg Template

Git commit message에 대한 일관된 형식을 제공한다. 
template은 google에서 검색 후 참조하였음 [Link](https://ujuc.github.io/2020/02/02/git-commit-message-template-man-deul-gi/).

```text
# <type>: <subject>
##### Subject 50 characters ################# -> |


# Body Message
######## Body 72 characters ####################################### -> |
# Issue Tracker Number or URL


# --- COMMIT END ---
# Type can be
#   feat    : new feature
#   fix     : bug fix
#   refactor: refactoring production code
#   style   : formatting, missing semi colons, etc; no code change
#   tidy    : clang-tidy result of previous commit
# ------------------
#     제목줄은 대문자로 시작.
#     제목줄은 마침표로 끝내지 않음.
#     본문과 제목에는 빈줄을 넣어서 구분.
#     본문에 목록을 나타낼때는 "-"로 시작.
# ------------------
```  

이를 적용하기 위해 사용자가 .commit-msg를 사용할 것이라고 git config에 설정해 줘야함.
```shell
git config --global commit.template ~/.commit-msg
```

추가적으로, 공동 작업을 다른 버젼으로 개발할 때 발생할 수 있는 혼선을 줄여보고자, 각 commit 당시 작업물의 version을 git hook을 통해 추가한다. 
version control source에 연동하여 작성하였으며, 언제든지 변경가능.
```
.git/hooks/prepare-commit-msg

COMMIT_MSG_FILE=$1
COMMIT_SOURCE=$2
SHA1=$3

VERSION_REGEX="VERSION_STRING (.*)"
BUILD_VERSION="$( cat version.h | grep -Eo "${VERSION_REGEX}")"
echo "${BUILD_VERSION}" >> ${COMMIT_MSG_FILE}
```