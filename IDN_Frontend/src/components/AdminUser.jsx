import { useEffect, useState } from "react";
import { getUsers } from "../api/adminApis";
import CreateUserForm from "../components/CreateUserForm";

export default function AdminUser() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const loadUser = async () => {
    setLoading(true);
    try {
      const res = await getUsers();
      setUsers(res.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadUser();
  }, []);

  return (
    <div className="bg-gray-100 min-h-screen p-3 sm:p-6">
      <div className="bg-white shadow rounded p-4 sm:p-6">
        {/* Header */}
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg sm:text-xl font-bold">Users</h2>

          <button
            className="bg-gray-400 hover:bg-gray-600 text-white px-4 py-2 rounded"
            onClick={() => setShowModal(true)}
          >
            ➕ Add User
          </button>
        </div>

        {/* Loading */}
        {loading && users.length === 0 ? (
          <p className="text-center">Loading...</p>
        ) : Array.isArray(users) && users.length === 0 ? (
          <div className="bg-blue-100 p-4 text-center rounded">
            <h3 className="font-bold">No User Available</h3>
            <p className="text-sm text-gray-500">Create new User</p>
          </div>
        ) : (
          <div className="w-full overflow-x-auto">
            <table className="min-w-[600px] w-full border border-gray-200 text-xs sm:text-sm">
              {/* Table Head */}
              <thead className="bg-gray-100">
                <tr>
                  <th className="px-2 sm:px-4 py-2 border">ID</th>
                  <th className="px-2 sm:px-4 py-2 border">Username</th>
                  <th className="px-2 sm:px-4 py-2 border">Email</th>
                  <th className="px-2 sm:px-4 py-2 border">Role</th>
                </tr>
              </thead>

              {/* Table Body */}
              <tbody>
                {users.map((user) => (
                  <tr key={user.id} className="text-center">
                    <td className="px-2 sm:px-4 py-2 border">{user.id}</td>

                    <td className="px-2 sm:px-4 py-2 border">
                      {user.userName || "N/A"}
                    </td>

                    <td className="px-2 sm:px-4 py-2 border break-all">
                      {user.email}
                    </td>

                    <td className="px-2 sm:px-4 py-2 border">
                      {user.role ? user.role.replace("ROLE_", "") : "N/A"}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* 🔥 Modal OUTSIDE main container */}
      {showModal && (
        <CreateUserForm
          onClose={() => setShowModal(false)}
          onSuccess={loadUser}
        />
      )}
    </div>
  );
}
