import { useEditor, EditorContent } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import { Node, Extension } from "@tiptap/core";
import { TextStyle } from "@tiptap/extension-text-style";
import DOMPurify from "dompurify";
import { useEffect, useState } from "react";

// Custom Font Size Extension
const FontSize = Extension.create({
  name: "fontSize",
  addOptions() {
    return { types: ["textStyle"] };
  },
  addGlobalAttributes() {
    return [
      {
        types: this.options.types,
        attributes: {
          fontSize: {
            default: null,
            parseHTML: (element) => element.style.fontSize || null,
            renderHTML: (attributes) => {
              if (!attributes.fontSize) return {};
              return { style: `font-size: ${attributes.fontSize}` };
            },
          },
        },
      },
    ];
  },
});

const ImageNode = Node.create({
  name: "image",
  group: "block",
  addAttributes() {
    return {
      src: {},
      align: { default: "center" },
      width: { default: "100%" },
      caption: { default: "" },
    };
  },
  parseHTML() {
    return [{ tag: "img" }];
  },
  renderHTML({ node }) {
    return [
      "div",
      { class: "news-image", "data-align": node.attrs.align },
      ["img", { src: node.attrs.src, style: `width:${node.attrs.width}` }],
      node.attrs.caption
        ? ["p", { class: "news-caption" }, node.attrs.caption]
        : null,
    ];
  },
});

const VideoNode = Node.create({
  name: "video",
  group: "block",
  addAttributes() {
    return { src: {} };
  },
  parseHTML() {
    return [{ tag: "video" }];
  },
  renderHTML({ HTMLAttributes }) {
    return [
      "video",
      { controls: true, class: "news-video", ...HTMLAttributes },
    ];
  },
});

const YoutubeNode = Node.create({
  name: "youtube",
  group: "block",
  addAttributes() {
    return { src: {} };
  },
  parseHTML() {
    return [{ tag: "iframe" }];
  },
  renderHTML({ HTMLAttributes }) {
    return [
      "iframe",
      {
        ...HTMLAttributes,
        width: "100%",
        height: "400",
        allowfullscreen: "true",
        class: "rounded-xl my-4",
      },
    ];
  },
});

export default function NewsEditor() {
  const [preview, setPreview] = useState("");

  const editor = useEditor({
    extensions: [
      StarterKit,
      TextStyle,
      FontSize,
      ImageNode,
      VideoNode,
      YoutubeNode,
    ],
    content: "<p>Start writing your news...</p>",
  });

  const addImage = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    const url = URL.createObjectURL(file);
    editor
      .chain()
      .focus()
      .insertContent({ type: "image", attrs: { src: url } })
      .run();
    e.target.value = null;
  };

  const addVideo = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    const url = URL.createObjectURL(file);
    editor
      .chain()
      .focus()
      .insertContent({ type: "video", attrs: { src: url } })
      .run();
    e.target.value = null;
  };

  const addYoutube = () => {
    const url = prompt("Enter YouTube URL");
    if (!url) return;
    const embed = url.replace("watch?v=", "embed/").split("&")[0];
    editor
      .chain()
      .focus()
      .insertContent({ type: "youtube", attrs: { src: embed } })
      .run();
  };

  const alignImage = (align) => {
    if (editor.isActive("image")) {
      editor.chain().focus().updateAttributes("image", { align }).run();
    } else {
      alert("Please click on an image first!");
    }
  };

  const addCaption = () => {
    if (editor.isActive("image")) {
      const text = prompt("Enter caption");
      editor.chain().focus().updateAttributes("image", { caption: text }).run();
    } else {
      alert("Please click on an image first!");
    }
  };

  const setFontSize = (size) => {
    if (size === "Font") return;
    if (editor.state.selection.empty) {
      alert("Please highlight the text you want to resize first!");
      return;
    }
    editor.chain().focus().setMark("textStyle", { fontSize: size }).run();
  };

  useEffect(() => {
    if (!editor) return;
    const interval = setInterval(() => {
      localStorage.setItem("draft", editor.getHTML());
    }, 2000);
    return () => clearInterval(interval);
  }, [editor]);

  useEffect(() => {
    if (!editor) return;
    const saved = localStorage.getItem("draft");
    if (saved) editor.commands.setContent(saved);
  }, [editor]);

  // Helper to clean HTML for both Preview and Saving
  const getCleanHTML = () => {
    return DOMPurify.sanitize(editor.getHTML(), {
      ADD_TAGS: ["iframe", "video", "source", "img"],
      ADD_ATTR: [
        "allowfullscreen",
        "controls",
        "class",
        "style",
        "data-align",
        "src",
        "width",
        "height",
      ],
      // THE FIX: Explicitly allow 'blob:' and 'data:' URLs
      ALLOWED_URI_REGEXP:
        /^(?:(?:(?:f|ht)tps?|mailto|tel|callto|cid|xmpp|data|blob|mediastream):|[^a-z]|[a-z+.\-]+(?:[^a-z+.\-:]|$))/i,
    });
  };

  // 1. Just shows the preview
  const handlePreview = () => {
    setPreview(getCleanHTML());
  };

  // 2. Handles saving for both Draft and Publish
  const handleSave = async (isDraft) => {
    const content = getCleanHTML();

    // This is the payload you will send to your database
    const postData = {
      content: content,
      isDraft: isDraft,
      // add other fields like title, authorId, etc. here
    };

    console.log(isDraft ? "Saving as Draft..." : "Publishing...", postData);

    // EXAMPLE API CALL:
    /*
    try {
      const response = await fetch('/api/posts', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(postData)
      });
      if (response.ok) {
        alert(isDraft ? "Draft saved successfully!" : "Published successfully!");
      }
    } catch (error) {
      console.error("Error saving post:", error);
    }
    */

    alert(
      isDraft ? "Draft saved! (Check console)" : "Published! (Check console)",
    );
  };

  if (!editor) return null;

  return (
    <div className="max-w-5xl mx-auto p-4">
      {/* TOOLBAR */}
      <div className="sticky top-0 bg-white border p-3 flex flex-wrap gap-2 rounded shadow z-50 items-center">
        <button
          onClick={() => editor.chain().focus().toggleBold().run()}
          className={`btn ${editor.isActive("bold") ? "bg-gray-300" : ""}`}
        >
          B
        </button>

        <select onChange={(e) => setFontSize(e.target.value)} className="btn">
          <option>Font</option>
          <option value="14px">Small</option>
          <option value="18px">Normal</option>
          <option value="24px">Large</option>
        </select>

        <label className="btn bg-blue-500 text-white cursor-pointer">
          Image
          <input type="file" accept="image/*" hidden onChange={addImage} />
        </label>

        <label className="btn bg-purple-500 text-white cursor-pointer">
          Video
          <input type="file" accept="video/*" hidden onChange={addVideo} />
        </label>

        <button onClick={addYoutube} className="btn">
          YouTube
        </button>

        <button onClick={() => alignImage("left")} className="btn">
          Left
        </button>
        <button onClick={() => alignImage("center")} className="btn">
          Center
        </button>
        <button onClick={() => alignImage("right")} className="btn">
          Right
        </button>

        <button onClick={addCaption} className="btn">
          Caption
        </button>

        {/* ACTION BUTTONS */}
        <div className="ml-auto flex gap-2">
          <button
            onClick={handlePreview}
            className="btn bg-gray-200 hover:bg-gray-300"
          >
            Preview
          </button>

          <button
            onClick={() => handleSave(true)}
            className="btn bg-yellow-500 text-white hover:bg-yellow-600"
          >
            Save Draft
          </button>

          <button
            onClick={() => handleSave(false)}
            className="btn bg-green-500 text-white hover:bg-green-600"
          >
            Publish
          </button>
        </div>
      </div>

      {/* EDITOR */}
      <div className="border p-6 rounded bg-white mt-4 min-h-[300px]">
        <EditorContent editor={editor} />
      </div>

      {/* PREVIEW */}
      {preview && (
        <div className="mt-10 p-6 border rounded bg-gray-50">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-xl font-bold">Preview</h2>
            <button
              onClick={() => setPreview("")}
              className="text-sm text-red-500 underline"
            >
              Close Preview
            </button>
          </div>
          <div
            className="ProseMirror"
            dangerouslySetInnerHTML={{ __html: preview }}
          />
        </div>
      )}

      {/* STYLES */}
      <style>{`
        .btn {
          padding: 6px 10px;
          border: 1px solid #ddd;
          border-radius: 6px;
        }

        .ProseMirror {
          font-family: Georgia, serif;
          font-size: 20px;
          line-height: 1.9;
          outline: none;
        }

        .news-image img {
          max-width: 100%;
          border-radius: 10px;
        }

        .news-image[data-align="left"] img {
          float: left;
          width: 50%;
          margin-right: 20px;
        }

        .news-image[data-align="right"] img {
          float: right;
          width: 50%;
          margin-left: 20px;
        }

        .news-caption {
          font-size: 14px;
          color: #666;
          text-align: center;
          margin-top: 5px;
          font-style: italic;
        }

        .news-video {
          width: 100%;
          margin: 20px 0;
          border-radius: 10px;
        }

        .ProseMirror::after {
          content: "";
          display: block;
          clear: both;
        }
      `}</style>
    </div>
  );
}
