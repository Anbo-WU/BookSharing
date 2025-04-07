package anu.g35.sharebooks.data.datasource;

import static java.lang.Integer.max;

import java.util.List;

import anu.g35.sharebooks.data.model.Book;

/**
 * AVL Tree implementation for searching books by ISBN
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-18
 */
public class BookAVLTree {
    /**
     * TreeNode class for AVL Tree, isbnKey is the key for the tree
     */
    static class TreeNode {
        long isbnKey;
        int nodeHeight;
        TreeNode leftChild, rightChild;
        Book bookData;

        TreeNode(Book book) {
            isbnKey = book.getIsbn();
            this.bookData = book;
            nodeHeight = 1;
        }
    }

    // rootNode of AVL Tree
    TreeNode rootNode;

    /** A utility function to get the nodeHeight of the tree
     *
     * @param N the TreeNode
     * @return the nodeHeight of the tree
     */
    int height(TreeNode N) {
        if (N == null)
            return 0;
        return N.nodeHeight;
    }

    /**
     * A utility function to right rotate subtree rooted with yNode
     * @param yNode the TreeNode
     * @return the new rootNode of the subtree
     */
    TreeNode rightRotate(TreeNode yNode) {
        TreeNode xNode = yNode.leftChild;
        TreeNode T2Node = xNode.rightChild;
        xNode.rightChild = yNode;
        yNode.leftChild = T2Node;
        yNode.nodeHeight = max(height(yNode.leftChild), height(yNode.rightChild)) + 1;
        xNode.nodeHeight = max(height(xNode.leftChild), height(xNode.rightChild)) + 1;
        return xNode;
    }

    /**
     * A utility function to left rotate subtree rooted with xNode
     * @param xNode the TreeNode
     * @return the new rootNode of the subtree
     */
    TreeNode leftRotate(TreeNode xNode) {
        TreeNode yNode = xNode.rightChild;
        TreeNode T2Node = yNode.leftChild;
        yNode.leftChild = xNode;
        xNode.rightChild = T2Node;
        xNode.nodeHeight = max(height(xNode.leftChild), height(xNode.rightChild)) + 1;
        yNode.nodeHeight = max(height(yNode.leftChild), height(yNode.rightChild)) + 1;
        return yNode;
    }
    /**
     * Get the balance factor of the TreeNode
     * @param N the TreeNode
     * @return the balance factor of the TreeNode
     */
    int getBalance(TreeNode N) {
        if (N == null)
            return 0;
        return height(N.leftChild) - height(N.rightChild);
    }

    /**
     * Insert a new book into the AVL Tree
     * @param node the rootNode of the tree
     * @param book the book to be inserted
     * @return the new rootNode of the tree
     */
    TreeNode insert(TreeNode node, Book book) {
        long key = book.getIsbn();
        if (node == null)
            return (new TreeNode(book));
        if (key < node.isbnKey)
            node.leftChild = insert(node.leftChild, book);
        else if (key > node.isbnKey)
            node.rightChild = insert(node.rightChild, book);
        else
            return node;

        node.nodeHeight = 1 + max(height(node.leftChild), height(node.rightChild));

        int balance = getBalance(node);

        if (balance > 1 && key < node.leftChild.isbnKey)
            return rightRotate(node);

        if (balance < -1 && key > node.rightChild.isbnKey)
            return leftRotate(node);

        if (balance > 1 && key > node.leftChild.isbnKey) {
            node.leftChild = leftRotate(node.leftChild);
            return rightRotate(node);
        }

        if (balance < -1 && key < node.rightChild.isbnKey) {
            node.rightChild = rightRotate(node.rightChild);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Search a book by ISBN in the AVL Tree
     * @param node the rootNode of the tree
     * @param key the ISBN of the book
     * @return the book with the given ISBN
     */
    TreeNode search(TreeNode node, long key) {
        if (node == null || node.isbnKey == key)
            return node;
        if (node.isbnKey < key)
            return search(node.rightChild, key);
        return search(node.leftChild, key);
    }

    /**
     * iterate the tree in order
     * @param node the rootNode of the tree
     * @param result the list of books
     */
    void getInorder(TreeNode node, List<Book> result) {
        if (node != null) {
            getInorder(node.leftChild, result);
            result.add(node.bookData);
            getInorder(node.rightChild, result);
        }
    }
}