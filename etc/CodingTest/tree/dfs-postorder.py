def dfs_postorder(cur_node):
    if cur_node is None:
        return
    
    dfs_postorder(cur_node.left)
    dfs_postorder(cur_node.right)
    print(cur_node.value)

dfs_postorder(root)