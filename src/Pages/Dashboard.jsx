import AdminUser from "../components/AdminUser";
import PostCard from "../components/PostCard";
import ContactMessages from "../components/ContactList";

export default function Dashboard() {
  return (
    <>
      <div>
        <PostCard />
        <AdminUser />
        <ContactMessages />
      </div>
    </>
  );
}
