memo = {}
def fibo(n):
    if n == 1 or n == 2 :
        return 1
    for i in range(3, n + 1):
        memo[i] = memo[i-1] + memo[i-2]
    
    return memo[n]