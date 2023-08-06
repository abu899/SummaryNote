def dfs_inorder(cur_node):
    if cur_node is None:
        return
    
    dfs_inorder(cur_node.left)
    print(cur_node.value)
    dfs_inorder(cur_node.right)

dfs_inorder(root)