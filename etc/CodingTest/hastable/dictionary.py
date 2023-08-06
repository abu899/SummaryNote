score = {
    'math': 97,
    'eng': 49,
    'kor': 89
}

print(score['math'])
score['math'] = 45

score['sci'] = 100

print('music' in score)

if 'music' in score :
    print (score['music'])
else:
    score['music'] = 80