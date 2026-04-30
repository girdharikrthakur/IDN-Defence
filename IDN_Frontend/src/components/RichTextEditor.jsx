import React from "react";
import { useEditor, EditorContent } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import Underline from "@tiptap/extension-underline";
import Link from "@tiptap/extension-link";
import Image from "@tiptap/extension-image";
import Placeholder from "@tiptap/extension-placeholder";
import TextAlign from "@tiptap/extension-text-align";
import Highlight from "@tiptap/extension-highlight";
import CodeBlockLowlight from "@tiptap/extension-code-block-lowlight";
import { lowlight } from "lowlight";

const MenuBar = ({ editor }) => {
    if (!editor) return null;

    const addImage = () => {
        const url = prompt("Enter image URL");
        if (url) editor.chain().focus().setImage({ src: url }).run();
    };

    const addLink = () => {
        const url = prompt("Enter URL");
        if (url) editor.chain().focus().setLink({ href: url }).run();
    };

    return (
        <div className="flex flex-wrap gap-2 p-2 border-b bg-gray-100 rounded-t-xl">

            {/* Bold */}
            <button onClick={() => editor.chain().focus().toggleBold().run()}>
                <b>B</b>
            </button>

            {/* Italic */}
            <button onClick={() => editor.chain().focus().toggleItalic().run()}>
                <i>I</i>
            </button>

            {/* Underline */}
            <button onClick={() => editor.chain().focus().toggleUnderline().run()}>
                U
            </button>

            {/* Strike */}
            <button onClick={() => editor.chain().focus().toggleStrike().run()}>
                S
            </button>

            {/* Highlight */}
            <button onClick={() => editor.chain().focus().toggleHighlight().run()}>
                Highlight
            </button>

            {/* Headings */}
            <button onClick={() => editor.chain().focus().toggleHeading({ level: 1 }).run()}>
                H1
            </button>
            <button onClick={() => editor.chain().focus().toggleHeading({ level: 2 }).run()}>
                H2
            </button>

            {/* Lists */}
            <button onClick={() => editor.chain().focus().toggleBulletList().run()}>
                • List
            </button>
            <button onClick={() => editor.chain().focus().toggleOrderedList().run()}>
                1. List
            </button>

            {/* Blockquote */}
            <button onClick={() => editor.chain().focus().toggleBlockquote().run()}>
                Quote
            </button>

            {/* Code */}
            <button onClick={() => editor.chain().focus().toggleCodeBlock().run()}>
                Code
            </button>

            {/* Align */}
            <button onClick={() => editor.chain().focus().setTextAlign("left").run()}>
                Left
            </button>
            <button onClick={() => editor.chain().focus().setTextAlign("center").run()}>
                Center
            </button>
            <button onClick={() => editor.chain().focus().setTextAlign("right").run()}>
                Right
            </button>

            {/* Link */}
            <button onClick={addLink}>Link</button>

            {/* Image */}
            <button onClick={addImage}>Image</button>

        </div>
    );
};

const RichTextEditor = ({ content, setContent }) => {
    const editor = useEditor({
        extensions: [
            StarterKit,
            Underline,
            Highlight,
            Link.configure({
                openOnClick: false,
            }),
            Image,
            TextAlign.configure({
                types: ["heading", "paragraph"],
            }),
            Placeholder.configure({
                placeholder: "Write your amazing post here...",
            }),
            CodeBlockLowlight.configure({
                lowlight,
            }),
        ],
        content,
        onUpdate: ({ editor }) => {
            setContent(editor.getHTML());
        },
    });

    return (
        <div className="border rounded-xl shadow-md bg-white">
            <MenuBar editor={editor} />
            <EditorContent
                editor={editor}
                className="p-4 min-h-[300px] focus:outline-none"
            />
        </div>
    );
};

export default RichTextEditor;