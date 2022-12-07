package com.aoc2022.day

import com.aoc2022.day.CMD.CD
import com.aoc2022.day.CMD.LS

class Day7 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        val dirs = parseTree(input).allDirs()

        return dirs
            .map { it.sumSize() }
            .filter { it <= 100000 }
            .sum()
    }

    override fun solve2(input: List<String>): Int {
        val maxSpace = 70000000
        val desiredFree = 30000000

        val root = parseTree(input)
        val dirs = root.allDirs()
        val usedSpace = root.sumSize()
        val freeSpace = maxSpace - usedSpace

        return dirs.filter {
            val newFree = freeSpace + it.sumSize()
            newFree >= desiredFree
        }.minOf { it.sumSize() }
    }

    private fun parseTree(input: List<String>): TreeNode {
        var csr = TreeNode("/")

        var cmd = CD
        var param = ""
        for (line in input) {
            val newCmd = CMD.values().firstOrNull { line.startsWith(it.commandPrefix) }
            if (newCmd != null) {
                cmd = newCmd
                param = line.replace(newCmd.commandPrefix, "").trim()
                // exec command
                if (cmd == CD) {
                    csr = csr.cd(param)
                }
                continue
            }
            // parse output
            if (cmd == LS) {
                csr.addChild(TreeNode.fromStr(line))
            }
        }
        return csr.root()
    }
}

class TreeNode(val name: String, val size: Int = 0, val isDir: Boolean = true) {
    private val children = HashMap<String, TreeNode>()
    private var parent: TreeNode? = null

    fun addChild(child: TreeNode) {
        if (!children.containsKey(child.name)) {
            children[child.name] = child
            child.parent = this
        }
    }

    fun cd(dir: String) : TreeNode{
        return when (dir) {
            "/" -> {
                root()
            }
            ".." -> {
                parent!!
            }
            else -> { // create dir if it does not yet exist
                children.getOrElse(dir) {
                    val child = TreeNode(dir, size = 0, isDir = true)
                    addChild(child)
                    child
                }
            }
        }
    }

    fun sumSize() : Int {
        return children.values.sumOf { it.sumSize() } + size
    }

    fun root(): TreeNode {
        var res = this
        while (res.parent != null) {
            res = res.parent!!
        }
        return res
    }

    fun allDirs(): List<TreeNode> {
        val res = children.values.toList()
            .flatMap { it.allDirs() }
            .toMutableList()
        if(isDir) {
            res.add(this)
        }
        return res
    }

    companion object {
        fun fromStr(line: String) : TreeNode {
            return if (line.startsWith("dir ")) {
                TreeNode(line.substring("dir ".length), size = 0, isDir = true)
            } else {
                val (sizeStr, name) = line.split(" ")
                TreeNode(name, size = sizeStr.toInt(), isDir = false)
            }
        }
    }
}

enum class CMD {
    CD,
    LS;
    val commandPrefix get() = "$ " + this.toString().lowercase()
}