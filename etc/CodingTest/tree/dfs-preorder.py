def dfs_preorder(cur_node):
    if cur_node is None:
        return
    
    print(cur_node.value)
    dfs_preorder(cur_node.left)
    dfs_preorder(cur_node.right)

dfs_preorder(root)