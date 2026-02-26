---
name: git-auto-commit-pusher
description: 根据当前仓库已暂存变更生成精简中文提交注释，并自动执行 git commit 与 git push。
---

# Git 自动提交推送器

根据当前仓库变更生成中文提交文案，并自动提交与推送。

## 工作流

1. 优先读取已暂存差异（`git diff --staged`）。
2. 仅以已暂存内容为依据生成提交文案。
3. 生成单行中文提交注释。
4. 执行 `git commit -m "<生成的文案>"`。
5. 获取当前分支并执行 `git push origin <当前分支>`。

## 采集 Diff

优先使用命令：

```bash
git diff --staged
```

选择规则：

1. 只使用已暂存 diff（`git diff --staged`）。
2. 如果已暂存 diff 为空，返回：`无已暂存变更，已跳过 commit 与 push。`
3. 当 diff 为空时，不执行任何提交和推送命令。

## 生成规则

- 提交前缀固定使用 `feat`。
- 摘要必须精简、客观，仅使用中文。
- 若存在多个关键改动，使用中文逗号（`，`）在单行中连接。

## 输出模板

先生成文案，格式必须为：

```text
feat：<摘要1>，<摘要2>，<摘要3>
```

## 执行规则

- 生成文案后，必须执行：

```bash
git commit -m "feat：..."
```

- 当环境需要权限提升时，`git commit` 申请授权应附带：
  - `prefix_rule`: `["git","commit"]`
  - `justification`: 简要说明“提交当前已暂存变更”

- 然后执行：

```bash
branch=$(git rev-parse --abbrev-ref HEAD)
git push origin "$branch"
```

- 当环境需要权限提升时，`git push` 申请授权应附带：
  - `prefix_rule`: `["git","push"]`
  - `justification`: 简要说明“推送当前分支到 origin”

- 如果提交或推送失败，返回实际错误信息，不编造成功状态。
